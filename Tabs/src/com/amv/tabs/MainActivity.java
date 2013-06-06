package com.amv.tabs;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private TableLayout tabTableLayout;
    private TableLayout personTableLayout;
	private TextView name;
	private TextView price;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tabTableLayout = (TableLayout) findViewById(R.id.tabTableLayout);
        personTableLayout = (TableLayout) findViewById(R.id.personTableLayout);

        Button addPersonButton = (Button) findViewById(R.id.addPerson);
        addPersonButton.setOnClickListener(addPersonButtonListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private OnClickListener addPersonButtonListener = new OnClickListener() {
    	public void onClick(View v) {
    		makeTabButton("hello", 0);
    	}
    };


    private void makeTabButton(String tag, int index) {
        //gets person_info.xml view
        LayoutInflater inf = LayoutInflater.from(MainActivity.this);
        View personInfoView = inf.inflate(R.layout.person_info, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //set current View to be modified to be the AlertDialog
        builder.setView(personInfoView);

        final EditText personName = (EditText) personInfoView.findViewById(R.id.personName);
        final EditText personPrice = (EditText) personInfoView.findViewById(R.id.personPrice);
        final EditText num_orders = (EditText) personInfoView.findViewById(R.id.num_orders);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newTab = inflater.inflate(R.layout.new_tab, null);
        name = (TextView) newTab.findViewById(R.id.name);
        price = (TextView) newTab.findViewById(R.id.price);

        num_orders.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                LayoutInflater orderInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View newOrder = orderInflater.inflate(R.layout.new_order, null);

                personTableLayout.addView(newOrder);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        builder.setTitle("Add a new person!");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                name.setText(personName.getText());
                price.setText(personPrice.getText());

                tabTableLayout.addView(newTab);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setCancelable(true);

        //create and show AlertDialog
        AlertDialog setPerson = builder.create();
        setPerson.show();


    }
}
