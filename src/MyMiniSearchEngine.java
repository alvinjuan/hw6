import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// got help from the articles included
// got help from other students
// got help from qsc

public class MyMiniSearchEngine {
    // default solution. OK to change.
    // do not change the signature of index()
    private Map<String, List<List<Integer>>> indexes;

    // disable default constructor
    private MyMiniSearchEngine() {
    }

    public MyMiniSearchEngine(List<String> documents) {
        index(documents);
    }

    // each item in the List is considered a document.
    // assume documents only contain alphabetical words separated by white spaces.
    private void index(List<String> texts) {
        //homework
        indexes = new HashMap<>();

        for (int i = 0; i < texts.size(); i++){
            String[] words = texts.get(i).split(" ");
            for (int j = 0; j < words.length; j++){
                if (!indexes.containsKey(words[j])){
                    List<List<Integer>> array = new ArrayList<>();
                    for (int a = 0; a < texts.size(); a++){
                        array.add(new ArrayList<>());
                    }
                    indexes.put(words[j], array);
                }
                indexes.get(words[j]).get(i).add(j);
            }
        }
        System.out.println(indexes);
    }

    public static String ignoreCases(String string){
        string = string.toLowerCase();
        string = string.replaceAll("[^a-z]", "");
        return string;
    }

    // search(key) return all the document ids where the given key phrase appears.
    // key phrase can have one or two words in English alphabetic characters.
    // return an empty list if search() finds no match in all documents.
    public List<Integer> search(String keyPhrase) {
        // homework
        String[] phrase = keyPhrase.split(" ");
        phrase[0] = ignoreCases(phrase[0]);
        // Finds all values
        List<List<Integer>> result = indexes.get(phrase[0]);
        for(int i = 1; i < phrase.length; i++) {
            phrase[i] = ignoreCases(phrase[i]);
            // values for next word
            List<List<Integer>> holder = indexes.get(phrase[i]);
            if (holder == null){
                return new ArrayList<>();
            }
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j).isEmpty()) {
                    holder.get(j).clear();
                }
                else {
                    for (int a = 0; a < holder.get(j).size(); a++) {
                        int track = 0;
                        while (result.get(j).get(track) < holder.get(j).get(a)) {
                            track++;
                            if(track>=result.get(j).size()) {
                                break;
                            }
                        }
                        if(track > 0) {
                            --track;
                        }
                        if (result.get(j).get(track) != (holder.get(j).get(a) - 1)) {
                            holder.get(j).remove(a);
                            a--;
                        }
                    }
                }
            }
            result = holder;

        }
        List<Integer> solution = new ArrayList<>();
        if(result==null) {
            return solution;
        }
        for(int i = 0; i < result.size(); i++){
            if(!result.get(i).isEmpty()){
                solution.add(i);
            }
        }
        return solution;
    }
}
