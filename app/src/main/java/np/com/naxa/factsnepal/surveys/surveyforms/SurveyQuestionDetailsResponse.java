
package np.com.naxa.factsnepal.surveys.surveyforms;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyQuestionDetailsResponse {

    @SerializedName("survey_question")
    @Expose
    private List<SurveyQuestion> surveyQuestion = null;

    public List<SurveyQuestion> getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(List<SurveyQuestion> surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }


    @org.jetbrains.annotations.Contract(pure = true)
    public static String getDemoJson (){
        String json ="{\n" +
                "  \"survey_question\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"question\": \"Too Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 3,\n" +
                "          \"question\": \"Good\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"question\": \"Yes\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"question\": \"Not Sure\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"question\": \"Not Bad\",\n" +
                "          \"question_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"question\": \"Too god\",\n" +
                "          \"question_id\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Nepal Telecom is best\",\n" +
                "      \"question_type\": \"radio\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 17,\n" +
                "          \"question\": \"6\",\n" +
                "          \"question_id\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Ncell is best ?\",\n" +
                "      \"question_type\": \"checkbox\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 17,\n" +
                "          \"question\": \"6\",\n" +
                "          \"question_id\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Ncell is best ?\",\n" +
                "      \"question_type\": \"checkbox\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 17,\n" +
                "          \"question\": \"6\",\n" +
                "          \"question_id\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Ncell is best ?\",\n" +
                "      \"question_type\": \"checkbox\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 17,\n" +
                "          \"question\": \"6\",\n" +
                "          \"question_id\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is Ncell is best ?\",\n" +
                "      \"question_type\": \"checkbox\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 18,\n" +
                "          \"question\": \"5\",\n" +
                "          \"question_id\": 3\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is NTC is best ?\",\n" +
                "      \"question_type\": \"rating\",\n" +
                "      \"survey_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"id\": 18,\n" +
                "          \"question\": \"5\",\n" +
                "          \"question_id\": 3\n" +
                "        }\n" +
                "      ],\n" +
                "      \"question\": \"Is NTC is best ?\",\n" +
                "      \"question_type\": \"rating\",\n" +
                "      \"survey_id\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return json;
    }

}
