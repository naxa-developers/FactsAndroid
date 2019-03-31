package np.com.naxa.factsnepal.publicpoll;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class PublicPollResultActivity extends BaseActivity {

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_poll_public);
        setupToolbar();
        setSkillGraph();
    }

    @Deprecated
    private void setSkillGraph() {
        chart = (BarChart) findViewById(R.id.chart_poll_result);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();
        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Result");
        BARDATA = new BarData(Bardataset);
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(BARDATA);
        chart.setFitBars(true);

        chart.animateY(3000);
    }


    @Deprecated
    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(10f, 0));
        BARENTRY.add(new BarEntry(40f, 1));
        BARENTRY.add(new BarEntry(60f, 2));
        BARENTRY.add(new BarEntry(80f, 3));
        BARENTRY.add(new BarEntry(90f, 4));


    }

    @Deprecated
    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("Option 1");
        BarEntryLabels.add("Option 2");
        BarEntryLabels.add("Option 3");
        BarEntryLabels.add("Option 4");
        BarEntryLabels.add("Option 5");


    }
}
