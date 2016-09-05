package com.sudoku.gfm13.sudokusolver;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ButtonHandler> buttons;
    List<View> valueButtons;
    ButtonHandler lastClicked;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.sudokuLayout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        View.OnClickListener numberCycle = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button clicked = (Button) view;
                for (ButtonHandler button : buttons) {
                    if (button.Handled == clicked) {
                        if (button == lastClicked) {
                            button.CycleValue();
                        }
                        else {
                            if (lastClicked != null){
                                lastClicked.Deselect();
                            }
                            lastClicked = button;
                            button.Select();
                        }

                    }
                }
            }
        };

        createButtons(width, layout, numberCycle);
        addValueButtons();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void createButtons(int screenWidth, LinearLayout mainLayout, View.OnClickListener buttonAction) {
        buttons = new ArrayList<>(81);

        for (int i = 0; i < 9; i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < 9; j++) {
                Button button = new Button(this);
                button.setText("");

                button.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 9, screenWidth / 9));
                button.setBackground(determineColorByPosition(i, j));
                button.setOnClickListener(buttonAction);

                buttons.add(new ButtonHandler(i, j, button, getDrawable(R.drawable.selected_button_shape)));
                layout.addView(button);
            }

            mainLayout.addView(layout);
        }
    }

    private void addValueButtons()
    {
        valueButtons = new ArrayList<>(10);
        valueButtons.add(findViewById(R.id.button0));
        valueButtons.add(findViewById(R.id.button1));
        valueButtons.add(findViewById(R.id.button2));
        valueButtons.add(findViewById(R.id.button3));
        valueButtons.add(findViewById(R.id.button4));
        valueButtons.add(findViewById(R.id.button5));
        valueButtons.add(findViewById(R.id.button6));
        valueButtons.add(findViewById(R.id.button7));
        valueButtons.add(findViewById(R.id.button8));
        valueButtons.add(findViewById(R.id.button9));
    }

    private Drawable determineColorByPosition(int x, int y) {
        x = (int) x / 3;
        y = (int) y / 3;
        if (x % 2 == y % 2) {
            return getDrawable(R.drawable.dark_button_shape);
        } else {
            return getDrawable(R.drawable.light_button_shape);
        }
    }

    public void SolvePressed(View view) {

        int[][] values = new int[9][9];
        for (ButtonHandler button : buttons) {
            values[button.X][button.Y] = button.Value;
        }

        MainAlgorithm solver = new MainAlgorithm(values);

        if (solver.AttemptSolve()) {
            values = solver.GetBoardState();

            for (ButtonHandler buttonHandler : buttons) {
                buttonHandler.SetValue(values[buttonHandler.X][buttonHandler.Y]);
            }
        }
    }

    public void ClearPressed(View view) {
        for (ButtonHandler button : buttons) {
            button.SetValue(0);
        }
    }

    public void ValueButtonPressed(View view)
    {
        if (lastClicked != null) {
            lastClicked.SetValue(valueButtons.indexOf(view));
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sudoku.gfm13.sudokusolver/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sudoku.gfm13.sudokusolver/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
