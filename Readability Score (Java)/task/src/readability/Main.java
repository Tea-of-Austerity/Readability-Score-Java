package readability;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {

        String pathFileName = args[0];
        String fileContent ="";
        try{
            fileContent = readFileAsString(pathFileName);
        } catch(IOException e){
            System.out.println("Cannot read file: "+e.getMessage());
        }


        String[] words = fileContent.split(" ");
        String[] wordsTrimed  = wordsTrim(words);
        String[] sentences= fileContent.split("[\\.\\!\\?]");
        String[] sentencesTrimed = listTrim(sentences);

        double wordAmount = wordCount(sentencesTrimed);
        double sentenceAmount = sentenceCount(sentencesTrimed);
        double characterAmount = characterCount(fileContent);
        double syllableAmount = syllableCount(wordsTrimed);
        double polysyllableAmount = polysyllableCount(wordsTrimed);
        double scoreARI = calculateARI(characterAmount, wordAmount,sentenceAmount);
        double scoreFK = calculateFK(syllableAmount,wordAmount,sentenceAmount);
        double scoreSMOG = calculateSMOG(polysyllableAmount,sentenceAmount);
        double scoreCL = calculateCL(characterAmount, wordAmount,sentenceAmount);


        System.out.println("Words: "+ (int) wordAmount);
        System.out.println("Sentences: "+ (int) sentenceAmount);
        System.out.println("Characters: "+ (int) characterAmount);
        System.out.println("Syllables: " + (int)syllableAmount);
        System.out.println("Polysyllables: " + (int)polysyllableAmount);
        System.out.printf("The score is: %.2f%n",scoreARI);

        algorithmChoice(scoreARI,scoreFK,scoreSMOG,scoreCL);
    }
    static double calculateARI(double characterAmount, double wordAmount, double sentenceAmount){
        //implement algorithm for Automated Readability Index
        return 4.71*characterAmount/wordAmount + 0.5*wordAmount/sentenceAmount -21.43;
    }
    static double calculateFK(double syllableAmount, double wordAmount, double sentenceAmount){
        //implement algorithm for Flesch–Kincaid readability tests
        return 0.39*wordAmount/sentenceAmount+11.8*syllableAmount/wordAmount-15.59;
    }
    static double calculateSMOG(double polysyllableAmount, double sentenceAmount){
        //implement algorithm for Simple Measure of Gobbledygook
        return 1.043*Math.sqrt(polysyllableAmount*30/sentenceAmount)+3.1291;
    }
    static double calculateCL(double characterAmount, double wordAmount, double sentenceAmount){
        //implement algorithm for Coleman–Liau index
        double averageCharacterIn100Words = characterAmount/wordAmount*100;
        double averageSentencesIn100Words = sentenceAmount/wordAmount*100;
        return 0.0588*averageCharacterIn100Words-0.296*averageSentencesIn100Words -15.8;
    }
    public static String readFileAsString(String fileName) throws IOException{
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    static String[] wordsTrim(String[] words){
        String[] wordsTrim = new String[words.length];
        for(int i = 0; i<words.length;i++){
            wordsTrim[i] =words[i].replaceAll("\\W","");
        }
        System.out.println(Arrays.toString(wordsTrim));
        return wordsTrim;
    }

    static String[] listTrim(String[] sentences){
        String[] sentencesTrimed = new String[sentences.length];
        for (int i =0; i<sentences.length;i++){
            sentencesTrimed[i] = sentences[i].trim();
        }
        return sentencesTrimed;
    }
    static double wordCount(String[] sentencesTrimed){
        double sum = 0;
        for (int i=0; i<sentencesTrimed.length;i++){
            sum+= sentencesTrimed[i].split("\\s").length;
        }
        return sum;
    }
    static double characterCount(String fileContent){
        //return fileContent.split("[\\S]").length;
        double count =0;
        for(int i = 0; i<fileContent.length();i++){
            if(fileContent.charAt(i)!=' '){
                count++;
            }
        }
        return count;
    }
    static double syllableCount(String[] wordsTrimed){
        int totalCount = 0;
        for(String word:wordsTrimed) {
            totalCount+=syllableInWords(word);
        }
        return totalCount;
    }
    static double syllableInWords(String word){
        int vowelcount=0;
        for (int i =0;i<word.length();i++){
            if(word.substring(i,i+1).matches("[aeiouyAEIOUY]")){
                vowelcount++;
            }
        }
        //double vowel
        int doubleVowel =0;
        for(int i=0;i<word.length()-1;i++){
            if(word.substring(i,i+2).matches("[aeiouyAEIOUY]{2}")){
                doubleVowel++;
            }
        }
        //ending with e
        int endE = 0;
        if(word.substring(word.length()-1,word.length()).equals("e")){
            endE++;
        }
        return Math.max(vowelcount - doubleVowel - endE,1);
    }
    static double polysyllableCount(String[] wordsTrimed){
        int totalCount = 0;
        for(String word:wordsTrimed){
            if(syllableInWords(word)>2){
                totalCount++;
            }
        }
        return totalCount;
    }
    static double sentenceCount(String[] sentencesTrimmed){
        return sentencesTrimmed.length;
    }
    static void resultCateogry(double value,boolean all){
        String text="";
        if(all) {
            text = "This text should be understood by %d year-olds.%n";
        }else{
            text = "This text should be understood in average by %.2f-year-olds.";
        }
        if(value<=1){
            System.out.printf(text,categoryTable.ONE.getAge());
        }else if(value>1&&value<=2){
            System.out.printf(text,categoryTable.TWO.getAge());
        }else if(value>2&&value<=3){
            System.out.printf(text,categoryTable.THREE.getAge());
        }else if(value>3&&value<=4){
            System.out.printf(text,categoryTable.FOUR.getAge());
        }else if(value>4&&value<=5){
            System.out.printf(text,categoryTable.FIVE.getAge());
        }else if(value>5&&value<=6){
            System.out.printf(text,categoryTable.SIX.getAge());
        }else if(value>6&&value<=7){
            System.out.printf(text,categoryTable.SEVEN.getAge());
        }else if(value>7&&value<=8){
            System.out.printf(text,categoryTable.EIGHT.getAge());
        }else if(value>8&&value<=9){
            System.out.printf(text,categoryTable.NINE.getAge());
        }else if(value>9&&value<=10){
            System.out.printf(text,categoryTable.TEN.getAge());
        }else if(value>10&&value<=11){
            System.out.printf(text,categoryTable.ELEVEN.getAge());
        }else if(value>11&&value<=12){
            System.out.printf(text,categoryTable.TWELVE.getAge());
        }else if(value>12&&value<=13){
            System.out.printf(text,categoryTable.THIRTEEN.getAge());
        }else if(value>13){
            System.out.printf(text,categoryTable.FOURTEEN.getAge());
        }
    }
    static int resultCateogry(double value){
        if(value<=1){
            return categoryTable.ONE.getAge();
        }else if(value>1&&value<=2){
            return categoryTable.TWO.getAge();
        }else if(value>2&&value<=3){
            return categoryTable.THREE.getAge();
        }else if(value>3&&value<=4){
            return categoryTable.FOUR.getAge();
        }else if(value>4&&value<=5){
            return categoryTable.FIVE.getAge();
        }else if(value>5&&value<=6){
            return categoryTable.SIX.getAge();
        }else if(value>6&&value<=7){
            return categoryTable.SEVEN.getAge();
        }else if(value>7&&value<=8){
            return categoryTable.EIGHT.getAge();
        }else if(value>8&&value<=9){
            return categoryTable.NINE.getAge();
        }else if(value>9&&value<=10){
            return categoryTable.TEN.getAge();
        }else if(value>10&&value<=11){
            return categoryTable.ELEVEN.getAge();
        }else if(value>11&&value<=12){
            return categoryTable.TWELVE.getAge();
        }else if(value>12&&value<=13){
            return categoryTable.THIRTEEN.getAge();
        }else {
            return categoryTable.FOURTEEN.getAge();
        }
    }
    enum categoryTable{
        ONE (1,"5-6","Kindergarten",6),
        TWO (2,"6-7","First Grade",7),
        THREE (3,"7-8","Second Grade",8),
        FOUR (4,"8-9","Third Grade",9),
        FIVE (5,"9-10","Fourth Grade",10),
        SIX (6,"10-11","Fifth Grade",11),
        SEVEN (7,"11-12","Sixth Grade",12),
        EIGHT (8,"12-13","Seventh Grade",13),
        NINE (9,"13-14","Eighth Grade",14),
        TEN (10,"14-15","Ninth Grade",15),
        ELEVEN (11,"15-16","Tenth Grade",16),
        TWELVE (12,"16-17","Eleventh Grade",17),
        THIRTEEN (13,"17-18","Twelfth Grade",18),
        FOURTEEN (14,"18-22","College student",22);

        int score;
        String ageRange;
        String gradeLevel;
        int age;
        categoryTable(int score, String ageRange, String gradeLevel,int age){
            this.score = score;
            this.ageRange = ageRange;
            this.gradeLevel = gradeLevel;
            this.age = age;
        }
        public int getScore() {
            return score;
        }
        public String getAgeRange(){
            return ageRange;
        }
        public String getGradeLevel(){
            return gradeLevel;
        }
        public int getAge(){
            return age;
        }
    }
    static void algorithmChoice(double ARI, double FR, double SMOG, double CL){
        System.out.printf("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.equals("ARI")) {
            System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).%n",ARI,resultCateogry(ARI));
            resultCateogry(ARI,false);
        }else if(input.equals("FR")) {
            System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).%n",FR,resultCateogry(FR));
            resultCateogry(FR,false);
        }else if(input.equals("SMOG")) {
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).%n",SMOG,resultCateogry(SMOG));
            resultCateogry(SMOG,false);
        }else if(input.equals("CL")){
            System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).%n",CL,resultCateogry(CL));
            resultCateogry(CL,false);
        }else if(input.equals("all")){
            System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).%n",ARI,resultCateogry(ARI));
            System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).%n",FR,resultCateogry(FR));
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).%n",SMOG,resultCateogry(SMOG));
            System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).%n",CL,resultCateogry(CL));
            resultCateogry((ARI+FR+SMOG+CL)/4,true);
        }else{
            System.out.println("wrong inputs");
        }
    }
}






















