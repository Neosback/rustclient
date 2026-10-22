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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.runelite.cache.ObjectManager;
import net.runelite.cache.definitions.ObjectDefinition;
import net.runelite.cache.fs.Store;
import net.runelite.cache.util.Namer;
import net.runelite.data.App;
import net.runelite.data.dump.MediaWiki;
import net.runelite.data.dump.MediaWikiTemplate;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class SceneryDumper
{
	@Data
	@Builder
	public static final class SceneryData
	{
		private int id;
		private String name;
		private String category;
		private List<String> options;
		private String examine;
		private String location;
		private String wiki;
		private Integer worldX;
		private Integer worldY;
		private Integer plane;
		private Boolean interactable;
	}

	private static final Pattern POINT_COORD_PATTERN = Pattern.compile("[xX]\\s*[:=]\\s*(\\d+)[^\\d]+[yY]\\s*[:=]\\s*(\\d+)");
	private static final Pattern PLANE_PATTERN = Pattern.compile("plane\\s*[:=]\\s*(\\d+)");

	public static void dump(final Store store, final MediaWiki wiki, final File path) throws IOException
	{
		log.info("Dumping scenery classification and metadata to {}", path);

		final ObjectManager objectManager = new ObjectManager(store);
		objectManager.load();

		final Collection<ObjectDefinition> definitions = objectManager.getObjects();
		log.info("Loaded {} object definitions from cache store", definitions.size());

		final Map<Integer, String> locExamines = loadLocExamines();
		final Map<Integer, SceneryData> sceneryMap = new HashMap<>();

		for (final ObjectDefinition def : definitions)
		{
			if (def.getName() == null || def.getName().equalsIgnoreCase("NULL"))
			{
				continue;
			}

			final String cleanName = Namer.removeTags(def.getName()).replace('\u00A0', ' ').trim();
			if (cleanName.isEmpty())
			{
				continue;
			}

			final List<String> options = def.getActions() != null
				? Arrays.stream(def.getActions())
					.filter(a -> a != null && !a.equalsIgnoreCase("null") && !a.trim().isEmpty())
					.map(String::trim)
					.collect(Collectors.toList())
				: Collections.emptyList();

			final boolean interactable = !options.isEmpty();
			final String category = classifyCategory(cleanName, def.getActions());

			// For the AI catalog, classify all functional objects or interactable scenery
			if ("Other".equals(category) && !interactable)
			{
				continue;
			}

			final String examine = locExamines.get(def.getId());

			final SceneryData.SceneryDataBuilder builder = SceneryData.builder()
				.id(def.getId())
				.name(cleanName)
				.category(category)
				.options(options.isEmpty() ? null : options)
				.examine(examine)
				.interactable(interactable ? true : null);

			sceneryMap.put(def.getId(), builder.build());
		}

		final Map<Integer, SceneryData> sorted = new TreeMap<>(sceneryMap);
		final File outputFile = new File(path, "scenery_data.json");

		final Map<Integer, SceneryData> existingScenery = loadExistingScenery(outputFile);
		for (final Map.Entry<Integer, SceneryData> entry : existingScenery.entrySet())
		{
			final SceneryData cur = sceneryMap.get(entry.getKey());
			if (cur != null && entry.getValue().getWiki() != null)
			{
				cur.setWiki(entry.getValue().getWiki());
				if (entry.getValue().getLocation() != null)
				{
					cur.setLocation(entry.getValue().getLocation());
				}
				if (entry.getValue().getWorldX() != null)
				{
					cur.setWorldX(entry.getValue().getWorldX());
					cur.setWorldY(entry.getValue().getWorldY());
					cur.setPlane(entry.getValue().getPlane());
				}
				if (cur.getExamine() == null && entry.getValue().getExamine() != null)
				{
					cur.setExamine(entry.getValue().getExamine());
				}
			}
		}

		saveToFile(outputFile, sorted.values());
		log.info("Saved initial {} classified scenery objects to {}", sorted.size(), outputFile.getAbsolutePath());

		// Optional wiki enrichment for examine and coordinates
		if (wiki != null)
		{
			int count = 0;
			final int total = sorted.size();
			for (final Map.Entry<Integer, SceneryData> entry : sorted.entrySet())
			{
				final SceneryData data = entry.getValue();
				count++;

				if (data.getWiki() != null)
				{
					continue;
				}

				try
				{
					final Pair<String, String> pageData = wiki.getSpecialLookupData("object", data.getId(), 0);
					if (pageData != null && !Strings.isNullOrEmpty(pageData.getRight()))
					{
						final String wikitext = pageData.getRight();
						data.setWiki(pageData.getLeft());

						final MediaWikiTemplate template = MediaWikiTemplate.parseWikitext("Infobox Scenery", wikitext);
						if (template != null)
						{
							final String examine = template.getValue("examine");
							if (!Strings.isNullOrEmpty(examine))
							{
								data.setExamine(cleanWikiText(examine));
							}

							final String location = template.getValue("location");
							if (!Strings.isNullOrEmpty(location))
							{
								data.setLocation(cleanWikiText(location));
							}

							final String mapVal = template.getValue("map");
							if (mapVal != null)
							{
								final int[] coords = extractPointCoords(mapVal);
								if (coords != null)
								{
									data.setWorldX(coords[0]);
									data.setWorldY(coords[1]);
									data.setPlane(coords[2]);
								}
							}
						}
					}
				}
				catch (Exception ignored)
				{
				}

				if (count <= 3 || count % 25 == 0)
				{
					saveToFile(outputFile, sorted.values());
					log.info("Progressively saved scenery data: processed {}/{} objects", count, total);
				}
			}

			saveToFile(outputFile, sorted.values());
		}

		log.info("Dumped {} classified scenery objects to {}", sorted.size(), outputFile.getAbsolutePath());
	}

	private static final com.google.gson.Gson PRETTY_GSON = new com.google.gson.GsonBuilder()
		.setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	private static void saveToFile(final File file, final Collection<SceneryData> data)
	{
		if (file.getParentFile() != null)
		{
			file.getParentFile().mkdirs();
		}
		try (FileWriter fw = new FileWriter(file))
		{
			fw.write(App.GSON.toJson(data));
		}
		catch (IOException e)
		{
			log.warn("Failed saving scenery data to {}: {}", file.getAbsolutePath(), e.getMessage());
		}

		final File sampleFile = new File(file.getParentFile(), "scenery_sample.json");
		try (FileWriter fw = new FileWriter(sampleFile))
		{
			final List<SceneryData> sample = data.stream().limit(25).collect(Collectors.toList());
			fw.write(PRETTY_GSON.toJson(sample));
		}
		catch (Exception ignored)
		{
		}
	}

	private static Map<Integer, SceneryData> loadExistingScenery(final File outputFile)
	{
		final Map<Integer, SceneryData> map = new HashMap<>();
		if (outputFile == null || !outputFile.exists() || !outputFile.isFile())
		{
			return map;
		}

		try (final java.io.FileReader fr = new java.io.FileReader(outputFile))
		{
			final java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<SceneryData>>(){}.getType();
			final List<SceneryData> list = App.GSON.fromJson(fr, type);
			if (list != null)
			{
				for (final SceneryData s : list)
				{
					map.put(s.getId(), s);
				}
				log.info("Loaded {} existing scenery records from {}", map.size(), outputFile.getName());
			}
		}
		catch (Exception e)
		{
			log.warn("Could not read existing scenery from {}: {}", outputFile.getName(), e.getMessage());
		}

		return map;
	}

	private static Map<Integer, String> loadLocExamines()
	{
		final Map<Integer, String> examines = new HashMap<>();
		final File[] candidates = new File[]{
			new File("/Users/tylercovalt/Documents/RustOSRS/crates/osrs-editor/data/examines/locs.csv"),
			new File("crates/osrs-editor/data/examines/locs.csv"),
			new File("../RustOSRS/crates/osrs-editor/data/examines/locs.csv")
		};
		File target = null;
		for (final File f : candidates)
		{
			if (f.exists() && f.isFile())
			{
				target = f;
				break;
			}
		}
		if (target == null)
		{
			return examines;
		}

		try (final java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(target)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				final int commaIdx = line.indexOf(',');
				if (commaIdx <= 0)
				{
					continue;
				}
				try
				{
					final int id = Integer.parseInt(line.substring(0, commaIdx).trim());
					String text = line.substring(commaIdx + 1).trim();
					if (text.startsWith("\"") && text.endsWith("\"") && text.length() >= 2)
					{
						text = text.substring(1, text.length() - 1).replace("\"\"", "\"");
					}
					examines.put(id, text);
				}
				catch (NumberFormatException ignored)
				{
				}
			}
			log.info("Loaded {} examine tooltips from {}", examines.size(), target.getAbsolutePath());
		}
		catch (Exception e)
		{
			log.warn("Failed reading locs.csv: {}", e.getMessage());
		}
		return examines;
	}

	public static String classifyCategory(final String rawName, final String[] actions)
	{
		final String name = rawName.toLowerCase();
		final String actStr = actions != null ? String.join(" ", actions).toLowerCase() : "";

		// 1. Crafting
		if (name.contains("anvil") || name.contains("furnace") || name.contains("range")
			|| name.contains("stove") || name.contains("spinning wheel") || name.contains("pottery wheel")
			|| name.contains("pottery oven") || name.contains("loom") || name.contains("brewery")
			|| containsWord(name, "vat") || name.contains("cauldron") || name.contains("mill")
			|| name.contains("hopper") || name.contains("tanner")
			|| actStr.contains("smith") || actStr.contains("smelt") || actStr.contains("cook")
			|| actStr.contains("spin") || actStr.contains("weave"))
		{
			return "Crafting";
		}

		// 2. Surfaces
		if (name.contains("table") || name.contains("counter") || name.contains("desk")
			|| name.contains("workbench") || name.contains("pedestal") || name.contains("stand")
			|| (containsWord(name, "bar") && !name.contains("barrel") && !name.contains("barrier") && !name.contains("barricade") && !name.contains("barrows")))
		{
			return "Surfaces";
		}

		// 3. Seating
		if (name.contains("chair") || (name.contains("bench") && !name.contains("workbench")) || name.contains("stool")
			|| name.contains("throne") || name.contains("seat") || name.contains("couch")
			|| name.contains("pew") || name.contains("sofa")
			|| actStr.contains("sit-on") || actStr.contains("sit"))
		{
			return "Seating";
		}

		// 4. Sleeping
		if (name.contains("bed") || name.contains("hammock") || containsWord(name, "cot")
			|| name.contains("mattress") || actStr.contains("sleep") || actStr.contains("lie-on"))
		{
			return "Sleeping";
		}

		// 5. Lighting
		if (name.contains("candle") || name.contains("torch") || name.contains("lantern")
			|| name.contains("fireplace") || name.contains("brazier") || name.contains("sconce")
			|| name.contains("lamp") || name.contains("candelabra") || name.contains("chandelier")
			|| (containsWord(name, "fire") && !name.contains("firework"))
			|| actStr.contains("light") || actStr.contains("extinguish"))
		{
			return "Lighting";
		}

		// 6. Entrances
		if (name.contains("door") || name.contains("gate") || name.contains("curtain")
			|| name.contains("trapdoor") || name.contains("gateway") || name.contains("portal")
			|| name.contains("archway") || name.contains("portcullis")
			|| actStr.contains("open") || actStr.contains("close") || actStr.contains("enter"))
		{
			return "Entrances";
		}

		// 7. Storage
		if (name.contains("crate") || name.contains("chest") || name.contains("barrel")
			|| name.contains("cupboard") || name.contains("drawers") || name.contains("wardrobe")
			|| name.contains("dresser") || name.contains("basket") || name.contains("sack")
			|| name.contains("shelf") || name.contains("shelves") || name.contains("cabinet")
			|| name.contains("coffer") || containsWord(name, "bin") || name.contains("locker")
			|| actStr.contains("search"))
		{
			return "Storage";
		}

		// 8. Vertical (ladders & stairs)
		if (name.contains("ladder") || name.contains("stairs") || name.contains("staircase")
			|| name.contains("rope") || name.contains("steps")
			|| actStr.contains("climb-up") || actStr.contains("climb-down") || actStr.contains("climb"))
		{
			return "Vertical";
		}

		// 9. Religious (altars & shrines)
		if (name.contains("altar") || name.contains("shrine") || name.contains("statue")
			|| containsWord(name, "font") || actStr.contains("pray"))
		{
			return "Religious";
		}

		// 10. Water sources
		if (name.contains("sink") || (containsWord(name, "well") && !name.contains("dwelling")) || name.contains("pump")
			|| name.contains("fountain") || containsWord(name, "tap") || name.contains("spring"))
		{
			return "Water";
		}

		return "Other";
	}

	private static boolean containsWord(final String text, final String word)
	{
		final int idx = text.indexOf(word);
		if (idx == -1)
		{
			return false;
		}
		final boolean startOk = idx == 0 || !Character.isLetterOrDigit(text.charAt(idx - 1));
		final int end = idx + word.length();
		final boolean endOk = end == text.length() || !Character.isLetterOrDigit(text.charAt(end));
		return startOk && endOk;
	}

	private static int[] extractPointCoords(final String mapVal)
	{
		int plane = 0;
		final Matcher planeMatcher = PLANE_PATTERN.matcher(mapVal);
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

		final Matcher matcher = POINT_COORD_PATTERN.matcher(mapVal);
		if (matcher.find())
		{
			try
			{
				final int x = Integer.parseInt(matcher.group(1));
				final int y = Integer.parseInt(matcher.group(2));
				return new int[]{x, y, plane};
			}
			catch (NumberFormatException ignored)
			{
			}
		}

		return null;
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
