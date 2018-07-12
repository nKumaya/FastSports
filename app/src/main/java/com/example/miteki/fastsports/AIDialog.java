package com.example.miteki.fastsports;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import ai.api.AIServiceException;
import ai.api.PartialResultsListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.ui.AIButton;

public class AIDialog {

    private static final String TAG = ai.api.ui.AIDialog.class.getName();

    private final Context context;
    private final AIConfiguration config;

    private ai.api.ui.AIDialog.AIDialogListener resultsListener;
    private final Dialog dialog;
    private final AIButton aiButton;
    private final TextView partialResultsTextView;

    private final Handler handler;

    public interface AIDialogListener {
        void onResult(final AIResponse result);
        void onError(final AIError error);
        void onCancelled();
    }

    public AIDialog(final Context context, final AIConfiguration config) {
        this(context, config, ai.api.R.layout.aidialog);
    }

    public AIDialog(final Context context, final AIConfiguration config, final int customLayout) {
        this.context = context;
        this.config = config;
        dialog = new Dialog(context);
        handler = new Handler(Looper.getMainLooper());

        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(customLayout);

        partialResultsTextView = (TextView) dialog.findViewById(ai.api.R.id.partialResultsTextView);

        aiButton = (AIButton) dialog.findViewById(ai.api.R.id.micButton);
        aiButton.initialize(config);
        setAIButtonCallback(aiButton);
    }

    public void setResultsListener(final ai.api.ui.AIDialog.AIDialogListener resultsListener) {
        this.resultsListener = resultsListener;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void showAndListen() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                resetControls();
                dialog.show();
                startListening();
            }
        });
    }

    public AIResponse textRequest(final AIRequest request) throws AIServiceException {
        return aiButton.textRequest(request);
    }

    public AIResponse textRequest(final String request) throws AIServiceException {
        return textRequest(new AIRequest(request));
    }

    private void resetControls() {
        if (partialResultsTextView != null) {
            partialResultsTextView.setText("");
        }
    }

    private void setAIButtonCallback(final AIButton aiButton) {
        aiButton.setResultsListener(new AIButton.AIButtonListener() {
            @Override
            public void onResult(final AIResponse result) {

                AIDialog.this.close();

                if (resultsListener != null) {
                    resultsListener.onResult(result);
                }
            }

            @Override
            public void onError(final AIError error) {
                if (resultsListener != null) {
                    resultsListener.onError(error);
                }
            }

            @Override
            public void onCancelled() {

                AIDialog.this.close();

                if (resultsListener != null) {
                    resultsListener.onCancelled();
                }
            }
        });

        aiButton.setPartialResultsListener(new PartialResultsListener() {
            @Override
            public void onPartialResults(final List<String> partialResults) {
                final String result = partialResults.get(0);
                if (!TextUtils.isEmpty(result)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (partialResultsTextView != null) {
                                partialResultsTextView.setText(result);
                            }
                        }
                    });
                }
            }
        });

    }

    private void startListening() {
        if (aiButton != null) {
            aiButton.startListening();
        }
    }

    public void close() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    /**
     * Get AIService object for making different data requests
     * @return
     */
    public AIService getAIService() {
        return aiButton.getAIService();
    }

    /**
     * Disconnect aiDialog from the recognition service.
     * Use pause/resume methods when you have permanent reference to the AIDialog object in your Activity.
     * pause() call should be added to the onPause() method of the Activity.
     * resume() call should be added to the onResume() method of the Activity.
     */
    public void pause() {
        if (aiButton != null) {
            aiButton.pause();
        }
    }

    /**
     * Reconnect aiDialog to the recognition service.
     * Use pause/resume methods when you have permanent reference to the AIDialog object in your Activity.
     * pause() call should be added to the onPause() method of the Activity.
     * resume() call should be added to the onResume() method of the Activity.
     */
    public void resume() {
        if (aiButton != null) {
            aiButton.resume();
        }
    }
}
