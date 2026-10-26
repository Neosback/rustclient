/*
 * MIT License
 *
 * Copyright (c) 2026 Tyler Covalt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.runelite.data.dump.wiki;

import com.google.common.base.Strings;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.runelite.cache.fs.Store;
import net.runelite.data.App;
import net.runelite.data.dump.MediaWiki;
import net.runelite.data.dump.MediaWikiTemplate;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class BuildingDumper
{
	@Data
	@Builder
	public static final class BuildingData
	{
		private String name;
		private String wiki;
		private String archetype;
		private List<String> categories;
		private Integer worldX;
		private Integer worldY;
		private Integer plane;
		private List<int[]> polygon;
		private List<String> facilities;
		private Integer floors;
		private List<String> occupants;
		private String location;
		private String leagueRegion;
	}

	private static final List<String> TARGET_CATEGORIES = Arrays.asList(
		"Category:Banks",
		"Category:Bars and inns",
		"Category:Castles",
		"Category:Churches",
		"Category:Guilds",
		"Category:Houses and homes",
		"Category:Jails",
		"Category:Libraries",
		"Category:Manors and mansions",
		"Category:Municipal buildings",
		"Category:Shops",
		"Category:Temples",
		"Category:Buildings"
	);

	private static final Pattern COORD_PAIR_PATTERN = Pattern.compile("(\\d{4,5})\\s*,\\s*(\\d{4,5})");
	private static final Pattern X_COORD_PATTERN = Pattern.compile("[xX]\\s*[:=]\\s*(\\d+)");
	private static final Pattern Y_COORD_PATTERN = Pattern.compile("[yY]\\s*[:=]\\s*(\\d+)");
	private static final Pattern PLANE_PATTERN = Pattern.compile("plane\\s*[:=]\\s*(\\d+)");
	private static final Pattern WIKI_LINK_PATTERN = Pattern.compile("\\[\\[([^|\\]]+)(?:\\|([^\\]]+))?\\]\\]");

	public static void dump(final Store store, final MediaWiki wiki, final File path) throws IOException
	{
		log.info("Dumping building metadata to {}", path);

		final Map<String, BuildingData> buildings = new LinkedHashMap<>();
		final Set<String> visitedPages = new HashSet<>();

		final File outputFile = new File(path, "building_data.json");
		if (outputFile.getParentFile() != null)
		{
			outputFile.getParentFile().mkdirs();
		}

		for (final String category : TARGET_CATEGORIES)
		{
			log.info("Fetching members for {}", category);
			final List<String> pageTitles = wiki.getCategoryMembers(category, 0);
			log.info("Found {} pages in {}", pageTitles.size(), category);

			for (final String title : pageTitles)
			{
				final String cleanTitle = title.replace('_', ' ').trim();
				if (visitedPages.contains(cleanTitle))
				{
					final BuildingData existing = buildings.get(cleanTitle);
					if (existing != null && existing.getCategories() != null)
					{
						final String catName = category.replace("Category:", "");
						if (!existing.getCategories().contains(catName))
						{
							existing.getCategories().add(catName);
						}
					}
					continue;
				}
				visitedPages.add(cleanTitle);

				try
				{
					final Pair<String, String> pageData = wiki.getPageData(title, -1);
					if (pageData == null || Strings.isNullOrEmpty(pageData.getRight()))
					{
						continue;
					}

					final String wikitext = pageData.getRight();
					final MediaWikiTemplate template = findBuildingTemplate(wikitext);

					final BuildingData building = buildBuildingData(cleanTitle, pageData.getLeft(), category, wikitext, template);
					if (building != null)
					{
						buildings.put(cleanTitle, building);
						log.info("Scraped building: {} [{}] at ({}, {})", building.getName(), building.getArchetype(), building.getWorldX(), building.getWorldY());
						final int count = buildings.size();
						if (count <= 3 || count % 5 == 0)
						{
							saveToFile(outputFile, buildings.values());
						}
					}
				}
				catch (Exception ex)
				{
					log.warn("Failed to scrape building page {}: {}", title, ex.getMessage());
				}
			}
		}

		saveToFile(outputFile, buildings.values());
		log.info("Dumped {} total buildings to {}", buildings.size(), outputFile.getAbsolutePath());
	}

	private static final com.google.gson.Gson PRETTY_GSON = new com.google.gson.GsonBuilder()
		.setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	private static void saveToFile(final File file, final Collection<BuildingData> data)
	{
		try (FileWriter fw = new FileWriter(file))
		{
			fw.write(App.GSON.toJson(data));
		}
		catch (IOException e)
		{
			log.warn("Failed to progressively save building data to {}: {}", file.getAbsolutePath(), e.getMessage());
		}

		final File sampleFile = new File(file.getParentFile(), "building_sample.json");
		try (FileWriter fw = new FileWriter(sampleFile))
		{
			final List<BuildingData> sample = data.stream().limit(25).collect(Collectors.toList());
			fw.write(PRETTY_GSON.toJson(sample));
		}
		catch (Exception ignored)
		{
		}
	}

	private static MediaWikiTemplate findBuildingTemplate(final String wikitext)
	{
		final String[] templateNames = {"Infobox Location", "Infobox Shop", "Infobox Building", "Infobox Pub", "Infobox Inn"};
		for (final String name : templateNames)
		{
			final MediaWikiTemplate template = MediaWikiTemplate.parseWikitext(name, wikitext);
			if (template != null)
			{
				return template;
			}
		}
		return null;
	}

	private static BuildingData buildBuildingData(
		final String pageTitle,
		final String wikiSlug,
		final String sourceCategory,
		final String wikitext,
		final MediaWikiTemplate template
	)
	{
		String name = template != null ? template.getValue("name") : null;
		if (Strings.isNullOrEmpty(name))
		{
			name = pageTitle;
		}
		name = cleanWikiText(name);

		final String catName = sourceCategory.replace("Category:", "");
		final List<String> categories = new ArrayList<>(Collections.singletonList(catName));

		final String archetype = deduceArchetype(name, sourceCategory, template, wikitext);
		final List<int[]> polygon = new ArrayList<>();
		final int[] coords = extractCoordinates(wikitext, template, polygon);

		final Integer worldX = coords != null ? coords[0] : null;
		final Integer worldY = coords != null ? coords[1] : null;
		final Integer plane = coords != null ? coords[2] : 0;

		final List<String> facilities = extractFacilities(wikitext, template);
		final Integer floors = extractFloors(wikitext, template);
		final List<String> occupants = extractOccupants(template);

		String location = template != null ? template.getValue("location") : null;
		if (location != null)
		{
			location = cleanWikiText(location);
		}

		String leagueRegion = template != null ? template.getValue("leagueRegion") : null;
		if (leagueRegion != null)
		{
			leagueRegion = cleanWikiText(leagueRegion);
		}

		return BuildingData.builder()
			.name(name)
			.wiki(wikiSlug)
			.archetype(archetype)
			.categories(categories)
			.worldX(worldX)
			.worldY(worldY)
			.plane(plane)
			.polygon(polygon.isEmpty() ? null : polygon)
			.facilities(facilities.isEmpty() ? null : facilities)
			.floors(floors)
			.occupants(occupants.isEmpty() ? null : occupants)
			.location(location)
			.leagueRegion(leagueRegion)
			.build();
	}

	public static String deduceArchetype(
		final String name,
		final String category,
		final MediaWikiTemplate template,
		final String wikitext
	)
	{
		final String lowerName = name.toLowerCase();
		final String lowerCat = category.toLowerCase();
		final String special = template != null && template.getValue("special") != null
			? template.getValue("special").toLowerCase()
			: "";

		if (lowerCat.contains("bank") || lowerName.endsWith(" bank") || lowerName.contains("bank "))
		{
			return "Bank";
		}
		if (lowerCat.contains("bars") || lowerCat.contains("inns")
			|| lowerName.endsWith(" inn") || lowerName.endsWith(" pub")
			|| lowerName.endsWith(" tavern") || lowerName.endsWith(" bar"))
		{
			return "Tavern";
		}
		if (lowerCat.contains("castle") || lowerName.endsWith(" castle"))
		{
			return "Castle";
		}
		if (lowerCat.contains("church") || lowerCat.contains("temple")
			|| lowerName.contains("church") || lowerName.contains("temple")
			|| lowerName.contains("altar") || lowerName.contains("monastery")
			|| lowerName.contains("chapel"))
		{
			return "Church";
		}
		if (lowerName.contains("smith") || lowerName.contains("anvil")
			|| lowerName.contains("forge") || lowerName.contains("axe")
			|| lowerName.contains("sword") || special.contains("axe")
			|| special.contains("sword") || special.contains("metal")
			|| special.contains("pickaxe"))
		{
			return "Blacksmith";
		}
		if (lowerCat.contains("guild") || lowerName.endsWith(" guild"))
		{
			return "Guild";
		}
		if (lowerCat.contains("library") || lowerName.contains("library"))
		{
			return "Library";
		}
		if (lowerCat.contains("jail") || lowerName.contains("jail") || lowerName.contains("prison"))
		{
			return "Jail";
		}
		if (lowerCat.contains("manor") || lowerCat.contains("mansion")
			|| lowerName.endsWith(" manor") || lowerName.endsWith(" mansion"))
		{
			return "Manor";
		}
		if (lowerCat.contains("shops") || lowerName.contains("shop")
			|| lowerName.contains("store") || (template != null && template.containsKey("owner")))
		{
			return "Shop";
		}
		if (lowerCat.contains("houses") || lowerCat.contains("homes")
			|| lowerName.contains("house") || lowerName.contains("home"))
		{
			return "House";
		}

		return "Building";
	}

	private static int[] extractCoordinates(
		final String wikitext,
		final MediaWikiTemplate template,
		final List<int[]> polygonOut
	)
	{
		String mapValue = template != null ? template.getValue("map") : null;
		if (mapValue == null)
		{
			final Matcher mapMatcher = Pattern.compile("\\{\\{Map\\|([^}]+)\\}\\}", Pattern.CASE_INSENSITIVE).matcher(wikitext);
			if (mapMatcher.find())
			{
				mapValue = mapMatcher.group(1);
			}
		}

		if (mapValue == null)
		{
			return null;
		}

		int plane = 0;
		final Matcher planeMatcher = PLANE_PATTERN.matcher(mapValue);
		if (planeMatcher.find())
		{
			try
			{
				plane = Integer.parseInt(planeMatcher.group(1));
			}
			catch (NumberFormatException ignored)
			{
			}
		}

		final Matcher pairMatcher = COORD_PAIR_PATTERN.matcher(mapValue);
		int sumX = 0;
		int sumY = 0;
		int count = 0;

		while (pairMatcher.find())
		{
			try
			{
				final int x = Integer.parseInt(pairMatcher.group(1));
				final int y = Integer.parseInt(pairMatcher.group(2));
				// Ignore screen width/height zoom values like 250, 250
				if (x >= 1000 && y >= 1000)
				{
					polygonOut.add(new int[]{x, y});
					sumX += x;
					sumY += y;
					count++;
				}
			}
			catch (NumberFormatException ignored)
			{
			}
		}

		if (count > 0)
		{
			return new int[]{sumX / count, sumY / count, plane};
		}

		final Matcher xMatcher = X_COORD_PATTERN.matcher(mapValue);
		final Matcher yMatcher = Y_COORD_PATTERN.matcher(mapValue);
		if (xMatcher.find() && yMatcher.find())
		{
			try
			{
				final int x = Integer.parseInt(xMatcher.group(1));
				final int y = Integer.parseInt(yMatcher.group(1));
				return new int[]{x, y, plane};
			}
			catch (NumberFormatException ignored)
			{
			}
		}

		return null;
	}

	private static List<String> extractFacilities(final String wikitext, final MediaWikiTemplate template)
	{
		final Set<String> facilities = new HashSet<>();
		final String lowerData = (wikitext + " " + (template != null ? template.getMap().toString() : "")).toLowerCase();

		if (lowerData.contains("bank booth") || lowerData.contains("tellers") || lowerData.contains("banker"))
		{
			facilities.add("bank_booth");
		}
		if (lowerData.contains("depositbox") || lowerData.contains("deposit box"))
		{
			facilities.add("deposit_box");
		}
		if (lowerData.contains("pollbooth") || lowerData.contains("poll booth"))
		{
			facilities.add("poll_booth");
		}
		if (lowerData.contains("vault"))
		{
			facilities.add("vault");
		}
		if (lowerData.contains("anvil"))
		{
			facilities.add("anvil");
		}
		if (lowerData.contains("furnace"))
		{
			facilities.add("furnace");
		}
		if (lowerData.contains("range") || lowerData.contains("stove") || lowerData.contains("cooking"))
		{
			facilities.add("range");
		}
		if (lowerData.contains("sink") || lowerData.contains("water pump") || lowerData.contains("well"))
		{
			facilities.add("water_source");
		}
		if (lowerData.contains("altar"))
		{
			facilities.add("altar");
		}
		if (lowerData.contains("bed"))
		{
			facilities.add("bed");
		}
		if (lowerData.contains("spinning wheel"))
		{
			facilities.add("spinning_wheel");
		}
		if (lowerData.contains("pottery wheel") || lowerData.contains("potter's wheel"))
		{
			facilities.add("pottery_wheel");
		}
		if (lowerData.contains("pottery oven"))
		{
			facilities.add("pottery_oven");
		}
		if (lowerData.contains("loom"))
		{
			facilities.add("loom");
		}
		if (lowerData.contains("ladder"))
		{
			facilities.add("ladder");
		}
		if (lowerData.contains("staircase") || lowerData.contains("stairs"))
		{
			facilities.add("stairs");
		}

		final List<String> list = new ArrayList<>(facilities);
		Collections.sort(list);
		return list;
	}

	private static Integer extractFloors(final String wikitext, final MediaWikiTemplate template)
	{
		if (template != null && template.containsKey("floors"))
		{
			final Integer f = template.getInt("floors");
			if (f != null && f > 0)
			{
				return f;
			}
		}

		final String lower = wikitext.toLowerCase();
		if (lower.contains("three-storey") || lower.contains("3-storey")
			|| lower.contains("third floor") || lower.contains("2nd floor"))
		{
			return 3;
		}
		if (lower.contains("two-storey") || lower.contains("2-storey")
			|| lower.contains("second floor") || lower.contains("first floor")
			|| lower.contains("upstairs"))
		{
			return 2;
		}

		return 1;
	}

	private static List<String> extractOccupants(final MediaWikiTemplate template)
	{
		if (template == null)
		{
			return Collections.emptyList();
		}

		final Set<String> occupants = new HashSet<>();
		final String[] keys = {"owner", "personnel", "tellers", "inhabitants", "members"};

		for (final String key : keys)
		{
			final String val = template.getValue(key);
			if (val == null)
			{
				continue;
			}

			final Matcher m = WIKI_LINK_PATTERN.matcher(val);
			while (m.find())
			{
				String occ = m.group(2) != null ? m.group(2) : m.group(1);
				occ = cleanWikiText(occ);
				if (!occ.isEmpty() && !occ.toLowerCase().contains("file:"))
				{
					occupants.add(occ);
				}
			}

			if (occupants.isEmpty())
			{
				final String clean = cleanWikiText(val);
				if (!clean.isEmpty())
				{
					occupants.add(clean);
				}
			}
		}

		final List<String> list = new ArrayList<>(occupants);
		Collections.sort(list);
		return list;
	}

	private static String cleanWikiText(final String input)
	{
		if (input == null)
		{
			return "";
		}
		return input
			.replaceAll("\\[\\[(?:[^|\\]]*\\|)?([^\\]]+)\\]\\]", "$1")
			.replaceAll("<[^>]+>", "")
			.replace('\u00A0', ' ')
			.trim();
	}
}
