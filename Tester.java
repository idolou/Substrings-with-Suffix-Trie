import java.util.Arrays;

/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;

	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {

		// Each function here should test a different class.

		// CharLinkedList
		testCharLinkedList();

		// SuffixTreeNode
		testSuffixTreeNode();

		// ReverseSuffixTree
		testReverseSuffixTree();

		// SuffixTree
		testSuffixTree();


		// Notifying the user that the code have passed all tests.
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}
	}

	/**
	 * This utility function will count the number of times it was invoked.
	 * In addition, if a test fails the function will print the error message.
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;

		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}

	/**
	 * Checks the CharLinkedList class.
	 */
	private static void testCharLinkedList(){
		CharLinkedList list = new CharLinkedListImpl();
		test(list.size() == 0, "The size of the list should be 0");
		list.add('a');
		test(list.size() == 1, "The size of the list should be 1");
		test(list.firstChar() == 'a', "The first char should be 'a'");
	}

	/**
	 * Checks the SuffixTreeNode class.
	 */
	private static void testSuffixTreeNode() {
		// test empty root
		SuffixTreeNode node = new SuffixTreeNodeImpl();
		test(node.getTotalWordLength() == 0, "node word length should be 0");
		test(node.getNumOfChildren() == 0, "node num of children should be 0");

		// test search, binary search, shiftChildren and addChild
		SuffixTreeNode child1 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node);
		SuffixTreeNode child2 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node);
		SuffixTreeNode child3 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node);
		SuffixTreeNode child4 = new SuffixTreeNodeImpl(CharLinkedList.from("mmmmm"), node);
		node.setChildren(new SuffixTreeNode[]{child1, child2, child3, child4, null, null, null, null}, 4);

		// binary search
		test(node.binarySearch('b', 0, 3) == child2, "search for 'b' should find child2");
		test(node.binarySearch('h', 0, 3) == child3, "search for 'h' should find child3");
		test(node.binarySearch('a', 0, 3) == child1, "search for 'a' should find child2");
		test(node.binarySearch('m', 0, 4) == child4, "search for 'm' should find child4");

		// search
		test(node.search('a') == child1, "search for 'a' should return child1");
		test(node.search('x') == null, "search for 'x' should fail");
		test(node.search('b') == child2, "search for 'a' should return child2");
		test(node.search('h') == child3, "search for 'h' should return child3");
		test(node.search('c') == null, "search for 'c' should fail");
		test(node.search('t') == null, "search for 'e' should fail");

		// add child
		SuffixTreeNode child5 = new SuffixTreeNodeImpl(CharLinkedList.from("dog"), node);
		test(node.getNumOfChildren()==4, "node should have 4 children");
		node.addChild(child5);
		test(node.getChildren()[2] == child5, "3rd child should be child5");
		test(node.getNumOfChildren()==5, "node should have 5 children");
		SuffixTreeNode child6 = new SuffixTreeNodeImpl(CharLinkedList.from("cat"), node);
		node.addChild(child6);
		test(node.getChildren()[2] == child6, "3rd child should be child6");
		SuffixTreeNode child7 = new SuffixTreeNodeImpl(CharLinkedList.from("zebra"), node);
		node.addChild(child7);
		test(node.getChildren()[6] == child7, "3rd child should be child7");
	}

	/**
	 * Checks the SuffixTree class.
	 */
	private static void testSuffixTree() {
		//test SuffixTree
		SuffixTree tree = new SuffixTreeImpl("mississippi");
		SuffixTree tree2 = new SuffixTreeImpl("california");

		// num Of Occurrences
		test(tree.getRoot().numOfOccurrences("s".toCharArray(), 0)==4 , "num Of Occurrences of 's' in tree should be 4");
		test(tree.getRoot().numOfOccurrences("ssi".toCharArray(), 0)==2,"num Of Occurrences of 'ssi' in tree should be 2");
		test(tree.getRoot().numOfOccurrences("ssi".toCharArray(), 3)==12,"num Of Occurrences of '' in tree should be 12");
		test(tree.getRoot().numOfOccurrences("a".toCharArray(), 0)==0,"num Of Occurrences of 'a' in tree should be 0");
		test(tree.getRoot().numOfOccurrences("im".toCharArray(),0 )==0,"num Of Occurrences of 'im' in tree should be 0");
		//find suffix leaf
		test(tree2.getRoot().findSuffixLeaf("nia$".toCharArray(),0).getNumOfChildren()==0,"'nia$' should be a suffix leaf in tree2");
		test(tree.getRoot().findSuffixLeaf("ppi$".toCharArray(),0).getNumOfChildren()==0,"'ppi$' should be a suffix leaf in tree");
		test(tree.getRoot().findSuffixLeaf("i$".toCharArray(),0).getNumOfChildren()==0,"'i$' should be a suffix leaf in tree");

		//find LCA
		SuffixTreeNode a = tree.getRoot().findSuffixLeaf("ppi$".toCharArray(),0);
		SuffixTreeNode b = tree.getRoot().findSuffixLeaf("i$".toCharArray(),0);
		SuffixTreeNode c = tree.getRoot().findSuffixLeaf("pi$".toCharArray(),0);

		test(a.findLCA(b)==tree.getRoot(),"should find root in tree");
		test(a.findLCA(c)==tree.getRoot().search('p'),"should find node 'p'");
		test(c.findLCA(b)==tree.getRoot(),"should find root in tree");

		//contain
		test(tree.contains("mis")==true,"tree should contain 'mis'");
		test(tree.contains("misi")==false,"tree should not contain 'misi'");
		test(tree2.contains("nia")==true,"tree2 should contain 'nia'");
		test(tree2.contains("alf")==false,"tree2 should not contain 'alf'");

		//num of leaves
		SuffixTree tree3 = new SuffixTreeImpl("abc");
		test(tree.getRoot().getDescendantLeaves()==12, "tree should have 12 leaves");
		test(tree2.getRoot().getDescendantLeaves()==11, "tree2 should have 11 leaves");
		test(tree3.getRoot().getDescendantLeaves()==4, "tree3 should have 4 leaves");

		//num of leaves (after Suffix Add)
		tree3.addSuffix("cd".toCharArray(), 0);
		test(tree3.getRoot().getDescendantLeaves()==5, "tree3 should have 5 leaves now");
		tree3.addSuffix("cda".toCharArray(), 0);
		test(tree3.getRoot().getDescendantLeaves()==6, "tree3 should have 6 leaves now");

		//num of children
		test(tree.getRoot().getNumOfChildren()==5, "tree should have 5 children");
		test(tree2.getRoot().getNumOfChildren()==9, "tree2 should have 9 children");
		test(tree3.getRoot().getNumOfChildren()==4, "tree2 should have 4 children");

	}

	/**
	 * Checks the ReverseSuffixTree class.
	 */
	private static void testReverseSuffixTree(){

	testPalindrome("mississippi", "issXssi");
		testPalindrome("abc", "X");
		testPalindrome("abbc", "bb");
		testPalindrome("deankazas", "aXa");
		testPalindrome("ahtrffrtuuuuuu", "trffrt");
		testPalindrome("aa", "aa");
		testPalindrome("a", "X");

	}

	private static void testPalindrome(String word, String expected){
		test(new ReverseSuffixTreeImpl(word).longestPalindrome().equals(expected), "Longest palindrome should be " + expected);
	}






	}



