package com.COMP3004.Maven;

import com.COMP3004.Maven.Adventure.allyCard;
import com.COMP3004.Maven.Adventure.foeCard;
import com.COMP3004.Maven.Adventure.testCard;
import com.COMP3004.Maven.Adventure.weaponCard;
import com.COMP3004.Maven.Rank.rankCard;
import com.COMP3004.Maven.Story.questCard;
import com.COMP3004.Maven.Story.tournamentCard;
import com.COMP3004.Maven.TableDeck.AdventureDeck;
import com.COMP3004.Maven.TableDeck.StoryDeck;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    
    /**
     * Test AllyCard constructor
     */
    public void testAllyCardConstructor()
    {
    	allyCard ac = new allyCard("theName",10,20, "theData1", "theData2", "theSpecialFeature");
    	assertEquals("theName", ac.getName());
    	assertEquals(10, ac.getData1());
    	assertEquals(20, ac.getData2());
    	assertEquals("theData1", ac.getData1Type());
    	assertEquals("theData2", ac.getData2Type());
    }
    
    /**
     *  Test FoeCard constructor and getter
     */
    public void testFoeCardConstructor()
    {
    	foeCard fc = new foeCard("theName", 10, 15);
    	int arr = fc.getStat(0);
    	assertEquals(10, arr);
    }
    
    /** test TestCard constructor
     * 
     */
    public void testTestCardConstructor()
    {
    	testCard tc = new testCard("theName");
    	assertEquals("theName", tc.getName());
    }
    
    /**
     *  test weaponCard constructor and getter
     */
    public void testWeaponCardConstructor()
    {
    	weaponCard wc = new weaponCard("theName", 20);
    	assertEquals(20, wc.getPoints());
    }
    
    /**
     * test AdventureDeck (all methods)
     */
    public void testAdventureDeck()
    {
    	AdventureDeck ad = new AdventureDeck();
    	assertEquals(126, ad.size());
    }
    
    /**
     *  test StoryDeck (all methods) 
     */
    public void testStoryDeck()
    {
    	StoryDeck sd = new StoryDeck();
    	assertEquals(25, sd.size());
    	StoryDeck sd2 = new StoryDeck(1);
    	assertEquals(17, sd2.size());
    }
    
    /**
     * test questCard constructor and getter
     */
    public void testQuestCard()
    {
    	questCard qc = new questCard("theQuest", 10, "key");
    	assertEquals(10, qc.getNumStages());
    }
    
    /**
     * test tournamentCard constructor 
     */
    public void testTournamentCard()
    {
    	tournamentCard tc = new tournamentCard("theName", 20);
    	assertEquals(20, tc.getShields());
    	assertEquals("theName", tc.getName());
    }
}
