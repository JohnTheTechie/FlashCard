package johnfatso.flashcard;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class CardFlashActivity extends FragmentActivity implements DBreader, FlashCardFragment.OnFlashcardInteractionListener {

    private final String LOG_TAG = "TAG";

    CardPagerAdapter adapter;
    ViewPager pager;
    int currentPage;

    private TextView counter;

    DictEntryRepository repository;

    AnimatorSet flipOutAnimator, flipInAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flash);

        counter = findViewById(R.id.counter_flashcard_activity);
        counter.setText("");

        repository = new DictEntryRepository(getApplication(), this);
        repository.getDataSet();

        Log.v(LOG_TAG, "FlashCard Activity created");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //TODO: implement if needed
    }

    @Override
    public void flipOutPager(Fragment fragment) {
        Log.v(LOG_TAG, "Animation started on pager | current page :" + pager.getCurrentItem());
        flipOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_out);
        Log.v(LOG_TAG, "FlipOut animation to be applied to the item: "+ pager.getCurrentItem());
        flipOutAnimator.setTarget(pager.getChildAt(pager.getCurrentItem()));
        //flipOutAnimator.setTarget();
        flipOutAnimator.start();
    }

    @Override
    public void flipInPager(Fragment fragment) {
        Log.v(LOG_TAG, "Animation ended on pager | current page :" + pager.getCurrentItem());
        flipInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_in);
        //flipInAnimator.setTarget(pager.getChildAt(pager.getCurrentItem()));
        flipInAnimator.setTarget(pager.getChildAt(pager.getCurrentItem()));
        flipInAnimator.start();
    }

    private class CardPagerAdapter extends FragmentPagerAdapter{

        DictEntry[] dataset;

        CardPagerAdapter(@NonNull FragmentManager fm, int behavior, DictEntry[] dataset) {
            super(fm, behavior);
            this.dataset = dataset;
            Log.v(LOG_TAG, "Adapter constructor completed");
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new FlashCardFragment();
            Bundle bundle = new Bundle();
            bundle.putString(FlashCardFragment.ARG_SPECIMEN_STRING, dataset[position].getDeutcheWort());
            bundle.putString(FlashCardFragment.ARG_TYPE_STRING, dataset[position].getDeutcheWortType());
            bundle.putString(FlashCardFragment.ARG_TRANSLATION_STRING, dataset[position].getEnglischeTranslation());

            fragment.setArguments(bundle);

            Log.v(LOG_TAG, "getItem function completed | for specimen :"+dataset[position].getDeutcheWort()+" | translation :"+ dataset[position].getEnglischeTranslation() );

            return fragment;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return dataset.length;
        }


    }

    void setupPager(DictEntry[] dataset){
        adapter = new CardPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, dataset);
        //configuring list
        pager = findViewById(R.id.cardPager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //TODO:handle counter more efficiently
                counter.setText(position+1+"/"+pager.getAdapter().getCount());
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                Log.v(LOG_TAG, "selected page :"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });



        Log.v(LOG_TAG, "Pager setup completed");
    }

    @Override
    public void DBreaderResponse(Object data) {
        DictEntry[] dataset = (DictEntry[]) data;

        Log.v(LOG_TAG, "response received to card flash activity | entries recieved : "+dataset.length);
        setupPager((DictEntry[])data);
        pager.setOffscreenPageLimit(10);
    }
}