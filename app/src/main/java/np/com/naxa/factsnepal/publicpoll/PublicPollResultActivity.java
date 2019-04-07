package np.com.naxa.factsnepal.publicpoll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

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

//    @Deprecated
    private void setSkillGraph() {
        chart = (BarChart) findViewById(R.id.chart_poll_result);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBarEntryLabels();
        AddValuesToBARENTRY();

        Bardataset = new BarDataSet(BARENTRY, "Result");
        Bardataset.setDrawValues(true);
        BARDATA = new BarData(Bardataset);
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//        setlegend();
        chart.setData(BARDATA);
        chart.setFitBars(true);

        initAndSetLegend();
//        setlegend();
        chart.animateY(3000);
    }


//    @Deprecated
    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(1, 20f, BarEntryLabels.get(0)));
        BARENTRY.add(new BarEntry(2, 40f, BarEntryLabels.get(1)));
        BARENTRY.add(new BarEntry(3,60f, BarEntryLabels.get(2) ));
        BARENTRY.add(new BarEntry(4,80f, BarEntryLabels.get(3)));
    }

//    @Deprecated
    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("Nepali Congress");
        BarEntryLabels.add("Nepal Communist Party");
        BarEntryLabels.add("Rastriya Janata Party");
        BarEntryLabels.add("Bibeksheel Sajha Party");
    }


    // TODO: 4/3/2019
    public void setlegend(){
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setMaxSizePercent(.95f);
        legend.setWordWrapEnabled(true);
        legend.setFormSize(10f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
//        legend.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] {  BarEntryLabels.get(0), BarEntryLabels.get(1), BarEntryLabels.get(2), BarEntryLabels.get(3) });
    }

    private void initAndSetLegend(){
        View legendIcon1 = findViewById(R.id.legend1);
        View legendIcon2 = findViewById(R.id.legend2);
        View legendIcon3 = findViewById(R.id.legend3);
        View legendIcon4 = findViewById(R.id.legend4);

        TextView tvLegendLabel1 = findViewById(R.id.tv_legend1);
        TextView tvLegendLabel2 = findViewById(R.id.tv_legend2);
        TextView tvLegendLabel3 = findViewById(R.id.tv_legend3);
        TextView tvLegendLabel4 = findViewById(R.id.tv_legend4);

        legendIcon1.setBackgroundColor(ColorTemplate.COLORFUL_COLORS[0]);
        legendIcon2.setBackgroundColor(ColorTemplate.COLORFUL_COLORS[1]);
        legendIcon3.setBackgroundColor(ColorTemplate.COLORFUL_COLORS[2]);
        legendIcon4.setBackgroundColor(ColorTemplate.COLORFUL_COLORS[3]);

        tvLegendLabel1.setText(BarEntryLabels.get(0));
        tvLegendLabel2.setText(BarEntryLabels.get(1));
        tvLegendLabel3.setText(BarEntryLabels.get(2));
        tvLegendLabel4.setText(BarEntryLabels.get(3));
    }
}
