using UnityEngine;
using UnityEditor;
using UnityEngine.TestTools;
using NUnit.Framework;
using System.Collections;

public class NewEditModeTest {

	[UnityTest]
	public IEnumerator NewEditModeTestWithEnumeratorPasses() {

        TestTournamentDeckGetSize();
        TestTournamentDeckIsFound();
        TestWeaponDeckRemove();
        TestWeaponDeckFind();
        TestTestDeckFindIndex();
        TestSquireDeckGetSize();
        TestSquireDeckIsFound();
        TestPlayerConstructor();

        yield return null;
	}

    private void TestTournamentDeckGetSize()
    {
        var tournamentDeck = new TournamentDeck();
        
        // Check initial size is 0
        Assert.Equals(0, tournamentDeck.getSize());

        // Create instance of Tournament Card
        tournamentCard tc = new tournamentCard("Sample Tournament Card", 10);
        // Add card to Tournament Deck
        tournamentDeck.add(tc);

        // Check added size is 1
        Assert.Equals(1, tournamentDeck.getSize());
    }

    private void TestTournamentDeckIsFound()
    {
        var tournamentDeck = new TournamentDeck();

     
        // Create instance of Tournament Card
        tournamentCard tc = new tournamentCard("Sample Tournament Card", 10);
        
        // Add card to Tournament Deck
        tournamentDeck.add(tc);

        // Check if card exist in deck
        Assert.Equals(true, tournamentDeck.isFound("Sample Tournament Card"));
    }

    private void TestWeaponDeckRemove()
    {
        var weaponDeck = new WeaponDeck();


        // Create instance of Weapon Card
        weaponCard wc = new weaponCard("Sample Weapon Card", 10);

        // Add card to Weapon Deck
        weaponDeck.add(wc);

        // Check remove added card
        Assert.Equals(true, weaponDeck.remove("Sample Weapon Card"));
    }

    private void TestWeaponDeckFind()
    {
        var weaponDeck = new WeaponDeck();


        // Create instance of Weapon Card
        weaponCard wc = new weaponCard("Sample Weapon Card", 10);

        // Add card to Weapon Deck
        weaponDeck.add(wc);

        // Check remove added card
        Assert.Equals(true, weaponDeck.find("Sample Weapon Card"));
    }

    private void TestTestDeckFindIndex()
    {
        var testDeck = new TestDeck();


        // Create instance of Test Card
        testCard tc = new testCard("Sample Test Card", 10);

        // Add card to Test Deck
        testDeck.add(tc);

        // Check find index added card
        Assert.Equals(0, testDeck.findIndex("Sample Test Card"));
    }

    private void TestSquireDeckGetSize()
    {
        var squireDeck = new SquireDeck();


        // Create instance of Squire Card
        squireCard sc = new squireCard("Sample Squire Card", 10);

        // Add card to Squire Deck
        squireDeck.add(sc);

        // Check find index added card
        Assert.Equals(1, squireDeck.getSize());
    }

    private void TestSquireDeckIsFound()
    {
        var squireDeck = new SquireDeck();


        // Create instance of Squire Card
        squireCard sc = new squireCard("Sample Squire Card", 10);

        // Add card to Squire Deck
        squireDeck.add(sc);

        // Check find index added card
        Assert.Equals(1, squireDeck.isFound("Sample Squire Card"));
    }

    private void TestPlayerConstructor()
    {
        var rc = new rankCard("RankCard1", "RankType1");
        var player = new Player("Player1", rc, "Type");

        // Check Player name, type and shields
        Assert.Equals("Player1", player.name);
        Assert.Equals("Type", player.type);
        Assert.Equals(0, player.shields);
    }


}
