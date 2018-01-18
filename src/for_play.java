import java.util.SortedMap;
import java.util.Iterator;
import java.nio.charset.Charset;


/**
 * CharsetTest
 *
 * @author Eugene Matyushkin
 */
public class for_play {

    public static void main(String[] args){
        SortedMap<String, Charset> charsetsMap = Charset.availableCharsets();
        System.out.println("Charsets available: "+charsetsMap.size());
        for(String name : charsetsMap.keySet()){
            Charset charset = charsetsMap.get(name);
            StringBuffer sb = new StringBuffer();
            sb.append(name);
            sb.append(" (");
            for(Iterator<String> aliases = charset.aliases().iterator();aliases.hasNext();){
                sb.append(aliases.next());
                if (aliases.hasNext())
                    sb.append(",");
            }
            sb.append(")");
            System.out.println(sb.toString());
        }
    }
}