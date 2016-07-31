/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Manasa
 */
public class GameParserTest {
    private final String[] stopWords = {"a", "an", "the"};
    
    private static Dictionary testDictionary;
    
    public GameParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testDictionary = GameDictionary.getInstance();
        
        GameAction axnInsert = new GameAction("insert");
        axnInsert.addPattern("put {thing} in {container}");
        axnInsert.addPattern("insert {thing} in {container}");
        testDictionary.addGameAction(axnInsert);
        
        GameAction axnPuton = new GameAction("puton");
        axnPuton.addPattern("put {thing} on {surface}");
        axnPuton.addPattern("put {thing} on top of {surface}");
        testDictionary.addGameAction(axnPuton);
        
        GameObject objBall = new GameObject("ball");
        objBall.addNoun("beach ball");
        objBall.addAdjective("beach");
        objBall.addAdjective("red");
        objBall.addProperty("thing");
        testDictionary.addGameObject(objBall);
        
        GameObject objBox = new GameObject("box");
        objBox.addNoun("wooden box");
        objBox.addAdjective("wooden");
        objBox.addAdjective("brown");
        objBox.addProperty("container");
        testDictionary.addGameObject(objBox);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parse method, of class GameParser.
     */
    @Test
    public void testParseEmptyUserInput() {
        System.out.println("parse");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        Command result = instance.parse(userInput);
        assertNotNull(result);
        assertEquals("Empty Input", result.errorMessage);
    }
    
    /**
     * Test match function when input matches with the pattern 
     * and contains two objects
     */
    @Test (expected = NoSuchElementException.class)
    public void testParseForMatch() {
        System.out.println("parse");
        String userInput = "abc";
        GameParser instance = GameParser.getInstance();
        Command result = instance.parse(userInput);
        assertNotNull(result);
        assertEquals("basket ball", result.object1);
        assertEquals("basket box ", result.object2);
    }

    /**
     * 
     */
    @Test
    public void testMatchWithMatchingParameters() {
        System.out.println("parse");
        GameParser instance = GameParser.getInstance();
        
        String[] wLIst = instance.tokenizeWords("Put basket ball in basket box");
        //String Pattern = "Put (\\D*) in (\\D*)";
        String Pattern = "Put {object} in {object}";
  
        boolean op = instance.match(wLIst, Pattern);
        assertNotNull(op);
        assertEquals(true, op);
    }
    
     //Test match function when input matches with the pattern and contains one object
    @Test
    public void testParseForMatch2() {
        System.out.println("parse");
        String userInput = "abc";
        GameParser instance = GameParser.getInstance();
        Command result = instance.parse(userInput);
        assertNotNull(result);
        assertEquals("basket ball ", result.object1);
    }

    @Test
    public void testMatchWithMatchingParameters2() {
        System.out.println("parse");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        
        String[] wLIst = new String[]{"Examine","basket", "ball"};
        String Pattern = "Examine {Object}";
  
        boolean op = instance.match(wLIst, Pattern);
        assertNotNull(op);
        assertEquals(true, op);
    }
    
    //Test match function when input does not match with the pattern
    @Test
    public void testMatchWithoutMatchingParameters() {
        System.out.println("parse");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        
        String[] wLIst = new String[]{"Put","basket", "ball","into","wooden","box"};
        String Pattern = "Put {Object} in {Object}";
  
        boolean op = instance.match(wLIst, Pattern);
        assertNotNull(op);
        assertEquals(false, op);
    }
    
    //Test match function when input does not match with the pattern
    @Test
    public void testMatchWithoutMatchingParameters2() {
        System.out.println("parse");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        
        String[] wLIst = new String[]{"Put","basket", "ball"};
        String Pattern = "Put {Object} in {Object}";
  
        boolean op = instance.match(wLIst, Pattern);
        assertNotNull(op);
        assertEquals(false, op);
    }
    
    //Test match function when input matches with the pattern and contains no objects
    @Test
    public void testMatchWithoutMatchingParameters2() {
        System.out.println("parse");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        
        String[] wLIst = new String[]{"Inventory"};
        String Pattern = "Inventory";
  
        boolean op = instance.match(wLIst, Pattern);
        assertNotNull(op);
        assertEquals(true, op);
    /**
     * Test of tokenizeword method with empty user input
     */
    @Test
    public void testTokenizeEmptyString(){
        System.out.println("tokenize");
        String userInput = "";
        GameParser instance = GameParser.getInstance();
        String[] result = instance.tokenizeWords(userInput);
        assertNull(result);
    }
    
    /**
     * Test of tokenizeWord method with single word
     */
    @Test
    public void testTokenizeSingleWordUserInput(){
        System.out.println("tokenize");
        String userInput = "Test";
        GameParser instance = GameParser.getInstance();
        String[] result = instance.tokenizeWords(userInput);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals("Test", result[0]);
    }
    
    /**
     * Test of tokenizeWord with multi-worded user input
     */
    @Test
    public void testTokenizeMultipleWordUserInput(){
        System.out.println("tokenize");
        String userInput = "This is test";
        GameParser instance = GameParser.getInstance();
        String[] result = instance.tokenizeWords(userInput);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("This", result[0]);
        assertEquals("is", result[1]);
        assertEquals("test", result[2]);
    }
    
    /**
     * Test removeStopWords with null word token
     */
    @Test
    public void testRemoveStopWordWithNullWordToken()
    {
        System.out.println("remove stop words");
        String[] wordTokens = null;
        GameParser instance = GameParser.getInstance();
        instance.removeStopWords(stopWords, wordTokens);
        assertNull(wordTokens);
    }
    
    /**
     * Test removeStopWords with empty word token
     */
    @Test
    public void testRemoveStopWordWithEmptyWordToken()
    {
        System.out.println("remove stop words");
        String[] wordTokens = {};
        GameParser instance = GameParser.getInstance();
        instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(0, wordTokens.length);
    }
    
    /**
     * Test removeStopWords with single word token
     * which is a stop word
     */
    @Test
    public void testRemoveStopWordOnlyWithSingleStopWordEntry()
    {
        System.out.println("remove stop words");
        String[] wordTokens = new String[]{"a"};
        GameParser instance = GameParser.getInstance();
        wordTokens = instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(0, wordTokens.length);
    }
    
    /**
     * Test removeStopWords with multiple words 
     * where all the words are stop words
     */
    @Test
    public void testRemoveStopWordOnlyWithMultipleStopWordEntry()
    {
        System.out.println("remove stop words");
        String[] wordTokens = new String[]{"a", "an", "the"};
        GameParser instance = GameParser.getInstance();
        wordTokens = instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(0, wordTokens.length);
    }
    
    /**
     * Test removeStopWords with no stop words
     */
    @Test
    public void testRemoveStopWordNoStopWordEntry()
    {
        System.out.println("remove stop words");
        String[] wordTokens = new String[]{"This", "is", "test"};
        GameParser instance = GameParser.getInstance();
        wordTokens = instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(3, wordTokens.length);
        assertEquals("This", wordTokens[0]);
        assertEquals("is", wordTokens[1]);
        assertEquals("test", wordTokens[2]);
    }
    
    /**
     * Test removeStopWords with one stop word
     * and 2 or more non-stop words
     */
    @Test
    public void testRemoveStopWordWithStopWordAndOtherEntry()
    {
        System.out.println("remove stop words");
        String[] wordTokens = new String[]{"This", "is", "a", "test"};
        GameParser instance = GameParser.getInstance();
        wordTokens = instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(3, wordTokens.length);
        assertEquals("This", wordTokens[0]);
        assertEquals("is", wordTokens[1]);
        assertEquals("test", wordTokens[2]);
    }
    
    /**
     * Test removeStopWords with two stop words
     * and 2 or more non-stop words
     */
    @Test
    public void testRemoveStopWordWithMultipleStopWordsAndOtherEntry()
    {
        System.out.println("remove stop words");
        String[] wordTokens = new String[]{"Put", "the", "ball", "in", "the", "box"};
        GameParser instance = GameParser.getInstance();
        wordTokens = instance.removeStopWords(stopWords, wordTokens);
        assertNotNull(wordTokens);
        assertEquals(4, wordTokens.length);
        assertEquals("Put", wordTokens[0]);
        assertEquals("ball", wordTokens[1]);
        assertEquals("in", wordTokens[2]);
        assertEquals("box", wordTokens[3]);
    }
    
    /**
     * Verify Dictionary correctly returns if a word is a verb
     */
    @Test
    public void testWordExistsInDictionary(){
        assertTrue(testDictionary.isDefined("ball"));
        assertTrue(testDictionary.isDefined("box"));
    }
    
    /**
     * Verify Dictionary correctly returns if a word is a noun
     */
    public void testWordIsNounInDictionary(){
        assertTrue(testDictionary.isNoun("beach ball"));
        assertTrue(testDictionary.isNoun("wooden ball"));
    }
    
    /**
     * Verify Dictionary correctly returns if a word is an adjective
     */
    public void testWordIsAdjectiveInDictionary(){
        assertTrue(testDictionary.isAdjective("beach"));
        assertTrue(testDictionary.isAdjective("red"));
        assertTrue(testDictionary.isAdjective("brown"));
        assertTrue(testDictionary.isAdjective("wooden"));
    }
    
    /**
     * Verify Dictionary correctly returns if a word is a verb
     * This should fail once GameDictionary correctly implements isVerb
     */
    @Test(expected = NoSuchElementException.class)
    public void testWordIsVerbInDictionary(){
        assertTrue(testDictionary.isVerb("put"));
    }
    
    /**
     * Verify Dictionary correctly returns actions associated with a verb
     * This should fail once GameDictionary correctly implements getActions
     */
    @Test(expected = NoSuchElementException.class)
    public void tesActionsAreReturntedByDictionary(){
        assertNotNull(testDictionary.getActions("put"));
        assertNotEquals(0, testDictionary.getActions("put").size());
    }
}
