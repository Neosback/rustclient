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

import net.runelite.data.dump.MediaWikiTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class BuildingDumperTest
{
	@Test
	void deducesArchetypesCorrectly()
	{
		assertEquals("Bank", BuildingDumper.deduceArchetype("Varrock West Bank", "Banks", null, ""));
		assertEquals("Tavern", BuildingDumper.deduceArchetype("Blue Moon Inn", "Bars and inns", null, ""));
		assertEquals("Blacksmith", BuildingDumper.deduceArchetype("Bob's Brilliant Axes", "Shops", null, ""));
		assertEquals("Castle", BuildingDumper.deduceArchetype("Lumbridge Castle", "Castles", null, ""));
		assertEquals("Church", BuildingDumper.deduceArchetype("Ardougne church", "Buildings", null, ""));
		assertEquals("Shop", BuildingDumper.deduceArchetype("Varrock General Store", "Shops", null, ""));
		assertEquals("House", BuildingDumper.deduceArchetype("Farmer's house", "Houses and homes", null, ""));
	}

	@Test
	void parsesShopInfobox()
	{
		final String wikitext = "{{Infobox Shop\n" +
			"|name = Bob's Brilliant Axes.\n" +
			"|location = [[Lumbridge]]\n" +
			"|owner = [[Bob]]\n" +
			"|special = [[Axe]]s\n" +
			"|map = {{Map|3228,3206|3234,3206|3234,3201|3228,3201|mtype=polygon}}\n" +
			"|leagueRegion = Misthalin\n" +
			"}}";

		final MediaWikiTemplate template = MediaWikiTemplate.parseWikitext("Infobox Shop", wikitext);
		assertNotNull(template);
		assertEquals("Bob's Brilliant Axes.", template.getValue("name"));
		assertEquals("[[Bob]]", template.getValue("owner"));
		assertEquals("Blacksmith", BuildingDumper.deduceArchetype("Bob's Brilliant Axes.", "Shops", template, wikitext));
	}
}
