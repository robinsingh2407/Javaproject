package com.example.robinsingh.javaproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffee cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    /*
     * This method is called when the plus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee cup", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Finds the name of the person
        EditText nameOfPerson = (EditText) findViewById(R.id.name_of_person);
        String nameOfPersonEntered = nameOfPerson.getText().toString();
        //Figure out if user wants whipped cream
        CheckBox hasWhippedCreamCheckBox = (CheckBox) findViewById(R.id.has_whipped_cream);
        boolean hasWhippedCream = hasWhippedCreamCheckBox.isChecked();
        //Figure out if user wants chocolate
        CheckBox hasChocolateCheckBox = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolate = hasChocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String pricemessage = createOrderSummary(nameOfPersonEntered, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order For" + nameOfPersonEntered);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*Calculates the total price of number of cups of coffees
     *
     * *@returns total price
     * @param whippedCreamPrice used if user wants whitecream or not
     * @param chocolatePrice used if user wants chocolate or not
     * */
    public int calculatePrice(boolean whippedCreamPrice, boolean chocolatePrice) {
        int basePrice = 5;
        if (whippedCreamPrice) {
            basePrice = basePrice + 1;
        }
        if (chocolatePrice == true) {
            basePrice = basePrice + 2;
        }
        return basePrice * quantity;

    }

    /*Print the Summary of the order
     *
     * @param addingNameOfPerson adds name of the person
     * @param price of order
     * @param addingWhippedCream is used whether user wants this topping or not
     * @param addingChocolate is used whether user wants this topping or not
     * @returns text summary
     * */
    public String createOrderSummary(String addingNameOfPerson, int price, boolean addingWhippedCream, boolean addingChocolate) {
        String pricemessage = "Name : " + addingNameOfPerson + "\n";
        pricemessage += "Has Whipped Cream :" + addingWhippedCream + "\n";
        pricemessage += "Has Chocolate :" + addingChocolate + "\n";
        pricemessage += "Quantity : " + quantity + "\n";
        pricemessage += "Total :$" + price;
        pricemessage += "\nThank You!!";
        return pricemessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
