package johnfatso.flashcard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/**
 * Simple fragment for holding a card and 3 texts to represent a flashcard
 * On touching the card, it is supposed to flip and expose the other side
 *
 * TODO: implement animation
 */
public class FlashCardFragment extends Fragment {
    // the fragment initialization parameters, 3 string for the flash card
    static final String ARG_SPECIMEN_STRING = "param1";
    static final String ARG_TYPE_STRING = "param2";
    static final String ARG_TRANSLATION_STRING = "param3";

    //Card animation status keepers
    private final int FLIP_STATUS_FACING_UP = 0x01;
    private final int FLIP_STATUS_FLIP_STARTED = 0x02;
    private final int FLIP_STATUS_FACING_DOWN = 0x03;


    // parameters to be initialized during creation of fragment
    private String specimen;
    private String type;
    private String translation;

    //to tell which of the textviews need to be set visible in current state
    private boolean isCardFacingUp;

    //the text views
    private TextView specimen_text;
    private TextView type_text;
    private TextView translation_text;

    private ImageView card;

    //referrence to the parent activity for communication
    private OnFlashcardInteractionListener mListener;

    private final String LOG_TAG = "TAG";

    public FlashCardFragment() {
        // Required empty public constructor
    }


    public static FlashCardFragment newInstance(String specimen, String type, String translation) {
        FlashCardFragment fragment = new FlashCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPECIMEN_STRING, specimen);
        args.putString(ARG_TYPE_STRING, type);
        args.putString(ARG_TRANSLATION_STRING, translation);
        fragment.setArguments(args);

        Log.v("TAG", "FlashCardFragment instance created for specimen "+specimen+" and translation "+translation);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //card is set to face up by default
        isCardFacingUp = true;

        //if arguments are passed, the thre strings are recovered from bundle
        if (getArguments() != null) {
            specimen = getArguments().getString(ARG_SPECIMEN_STRING);
            type = getArguments().getString(ARG_TYPE_STRING);
            translation = getArguments().getString(ARG_TRANSLATION_STRING);
            Log.v("TAG", "FlashCardFragment created for specimen "+specimen+" and translation "+translation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_flash_card, container, false);

        //from the inflated view, create references to the text views and card
        specimen_text = fragmentView.findViewById(R.id.fc_specimen);
        type_text = fragmentView.findViewById(R.id.fc_type);
        translation_text = fragmentView.findViewById(R.id.fc_translation);

        ImageView card=fragmentView.findViewById(R.id.flippercard);

        // set onClickListener for the card
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardTouched();
            }
        });

        setTextViewTexts();
        if(isCardFacingUp) configureTextViews(FLIP_STATUS_FACING_UP);
        else configureTextViews(FLIP_STATUS_FACING_DOWN);

        Log.v("TAG", "FlashCardFragment onCreateView completed for specimen "+ specimen_text.getText().toString()+" and translation "+translation_text.getText().toString());

        return fragmentView;
    }

    private void onCardTouched(){
        //change the card side flag and flip
        mListener.flipOutPager(this);
        configureTextViews(FLIP_STATUS_FLIP_STARTED);
        mListener.flipInPager(this);
        new waitAsyncTask().execute();


    }

    private void setTextViewTexts(){
        // set the texts from the initialization parameters
        specimen_text.setText(specimen);
        translation_text.setText(translation);
        type_text.setText(type);
    }

    private void configureTextViews(int flipperState){
        //flipping the card
        if(flipperState == FLIP_STATUS_FLIP_STARTED){
            Log.v(LOG_TAG, "flipping text");
            specimen_text.setVisibility(View.INVISIBLE);
            type_text.setVisibility(View.INVISIBLE);
            translation_text.setVisibility(View.INVISIBLE);
        }
        else if(flipperState == FLIP_STATUS_FACING_UP){
            Log.v(LOG_TAG, "car facing up");
            isCardFacingUp = true;
            specimen_text.setVisibility(View.VISIBLE);
            type_text.setVisibility(View.INVISIBLE);
            translation_text.setVisibility(View.INVISIBLE);
        }
        else if(flipperState == FLIP_STATUS_FACING_DOWN){
            Log.v(LOG_TAG, "card facing down");
            isCardFacingUp = false;
            specimen_text.setVisibility(View.INVISIBLE);
            type_text.setVisibility(View.VISIBLE);
            translation_text.setVisibility(View.VISIBLE);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFlashcardInteractionListener) {
            mListener = (OnFlashcardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFlashcardInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFlashcardInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void flipOutPager(Fragment fragment);
        void flipInPager(Fragment fragment);
    }

    private class waitAsyncTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(!isCardFacingUp) configureTextViews(FLIP_STATUS_FACING_UP);
            else configureTextViews(FLIP_STATUS_FACING_DOWN);
        }
    }
}
