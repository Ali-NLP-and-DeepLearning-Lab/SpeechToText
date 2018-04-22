package com.example.dell.speechtotext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DELL on 15-04-2018.
 */

public class SpeechConverterClass {
    private SpeechRecognizer speech;
    Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionClass";
    ViewGroup micLayout;
    private TextView fillColor;
    private Activity activity;
    private TextView returnedText;

    public void convertSpeech(Activity activity1){
        activity=activity1;

        returnedText = (TextView) activity1.findViewById(R.id.textView1);
        fillColor=(TextView)activity1.findViewById(R.id.fill_color);
        micLayout=(ViewGroup)activity1.findViewById(R.id.mic_layout);

        micLayout.setVisibility(View.VISIBLE);
        activity.findViewById(R.id.card).setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80);
        fillColor.setLayoutParams(Params1);
        speech = SpeechRecognizer.createSpeechRecognizer(activity);
        speech.setRecognitionListener(new speechListener());
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        speech.startListening(recognizerIntent);
    }

    class speechListener implements RecognitionListener
    {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.i(LOG_TAG, "onReadyForSpeech");
            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,60);
            fillColor.setLayoutParams(Params1);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.i(LOG_TAG, "onBeginningOfSpeech");
            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,55);
            fillColor.setLayoutParams(Params1);
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
            if(rmsdB>=0) {
                if (rmsdB>=3) {
                    LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 34);
                    fillColor.setLayoutParams(Params1);
                }
                else{
                    LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 54);
                    fillColor.setLayoutParams(Params1);
                }

            }
            else{
                if (rmsdB==-2) {
                    LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
                    fillColor.setLayoutParams(Params1);
                }
                else {
                    LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 26);
                    fillColor.setLayoutParams(Params1);
                }
            }
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.i(LOG_TAG, "onBufferReceived: " + buffer);
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(LOG_TAG, "onEndOfSpeech");

            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
            fillColor.setLayoutParams(Params1);

            speech.stopListening();
            activity.findViewById(R.id.card).setVisibility(View.GONE);
//            if (speech != null) {
//                speech.destroy();
//                Log.i(LOG_TAG, "destroy");
//            }
        }

        @Override
        public void onError(int error) {
            String errorMessage = getErrorText(error);
            Log.d(LOG_TAG, "FAILED " + errorMessage);
            returnedText.setText(errorMessage);
            speech.stopListening();
            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
            fillColor.setLayoutParams(Params1);
            activity.findViewById(R.id.card).setVisibility(View.GONE);

            if (speech != null) {
                speech.destroy();
                Log.i(LOG_TAG, "destroy");
            }
        }

        @Override
        public void onResults(Bundle results) {
            Log.i(LOG_TAG, "onResults");
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text = "";
            for (String result : matches)
                text += result + "\n";
            returnedText.setText(matches.get(0).toString());

            if (speech != null) {
                speech.destroy();
                Log.i(LOG_TAG, "destroy");
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.i(LOG_TAG, "onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.i(LOG_TAG, "onEvent");
        }
    }



    private static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
