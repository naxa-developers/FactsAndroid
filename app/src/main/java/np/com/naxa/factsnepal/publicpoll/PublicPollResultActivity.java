package np.com.naxa.factsnepal.publicpoll;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;

public class PublicPollResultActivity extends BaseActivity {

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    PostPublicPollAnswerResponse postPublicPollAnswerResponse;
    PublicPollQuestionDetail publicPollQuestionDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_poll_public);

        newIntent(getIntent());

        setupToolbar();
        setSkillGraph();
    }

    protected void newIntent(Intent intent){

        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("map");
        postPublicPollAnswerResponse = (PostPublicPollAnswerResponse) hashMap.get(KEY_OBJECT);
        publicPollQuestionDetail = (PublicPollQuestionDetail) hashMap.get(KEY_EXTRA_OBJECT);

        TextView tvQuestionTitle =  findViewById(R.id.tv_question_title);
        tvQuestionTitle.setText(publicPollQuestionDetail.getQuestion());
    }

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


    public void AddValuesToBARENTRY(){
        int position = -1;
        for (ResultData resultData :postPublicPollAnswerResponse.getResultData()){
            position++;
            BarEntryLabels.add(resultData.getQuestion());
            BARENTRY.add(new BarEntry(position, resultData.getTotalCount(), BarEntryLabels.get(position)));
        }
    }

    public void AddValuesToBarEntryLabels(){
        for (ResultData resultData :postPublicPollAnswerResponse.getResultData()){
            BarEntryLabels.add(resultData.getQuestion());
        }
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
        LinearLayout linearLayout = findViewById(R.id.legend_root_layout);

        ImageView imageView;
        TextView textView;
        int position = -1;
        for (ResultData resultData : postPublicPollAnswerResponse.getResultData()){
            position++;
            View relativeLayout = LayoutInflater.from(this).inflate(R.layout.chart_legend_layout, linearLayout, false);
            imageView = (ImageView)relativeLayout.findViewById(R.id.iv_legend);
            imageView.setBackgroundColor(ColorTemplate.COLORFUL_COLORS[position]);

            textView = (TextView)relativeLayout.findViewById(R.id.tv_legend);
            textView.setText(BarEntryLabels.get(position));
            linearLayout.addView(relativeLayout);

        }
    }
}
