package johnfatso.flashcard;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

class DictionaryParser {

    private static final String LOG_TAG = "DB";

    static ArrayList parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        try{
            Log.v(LOG_TAG, "parse function started");
            xmlPullParser.next();
            xmlPullParser.next();
            Log.v(LOG_TAG, "parse function tried");
            return readXML(xmlPullParser);
        }finally {
            Log.v(LOG_TAG, "Unprocessed error thrown to calling function");
        }
    }

    private static ArrayList<DictEntry> readXML(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        ArrayList<DictEntry> list = new ArrayList<DictEntry>();

        xmlPullParser.require(XmlPullParser.START_TAG, null, "root");
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = xmlPullParser.getName();
            if(name.equals("word")){
                list.add(readWord(xmlPullParser));
            }else {
                skip(xmlPullParser);
            }
        }

        return list;
    }

    private static DictEntry readWord(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        xmlPullParser.require(XmlPullParser.START_TAG, null, "word");
        String german = null;
        String english = null;
        String plural = null;

        Log.v(LOG_TAG, "reading word from the xml");

        while ((xmlPullParser.next() != XmlPullParser.END_TAG)){
            if(xmlPullParser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = xmlPullParser.getName();
            if (name.equals("german")) {
                german = readGermanWord(xmlPullParser);
            } else if (name.equals("german-plural")) {
                plural = readPluralForm(xmlPullParser);
            } else if (name.equals("english")) {
                english = readEnglishtranslation(xmlPullParser);
            } else {
                skip(xmlPullParser);
            }
        }

        return new DictEntry(german, plural, english, Calendar.getInstance().getTimeInMillis());
    }

    private static String readGermanWord(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        Log.v(LOG_TAG, "reading german tag from the xml");
        xmlPullParser.require(XmlPullParser.START_TAG, null, "german");
        String german = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, null, "german");
        return german;
    }

    private static String readEnglishtranslation(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        Log.v(LOG_TAG, "reading english tag from the xml");
        xmlPullParser.require(XmlPullParser.START_TAG, null, "english");
        String english = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, null, "english");
        return english;
    }

    private static String readPluralForm(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException{
        Log.v(LOG_TAG, "reading plural tag from the xml");
        xmlPullParser.require(XmlPullParser.START_TAG, null, "german-plural");
        String plural = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, null, "german-plural");
        return plural;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        Log.v(LOG_TAG, "reading text from the xml tag");
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            Log.v(LOG_TAG, "text read : "+result);
            parser.nextTag();
        }
        return result;
    }


    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        Log.v(LOG_TAG, "skipping the tag : "+parser.getName());
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }



}
