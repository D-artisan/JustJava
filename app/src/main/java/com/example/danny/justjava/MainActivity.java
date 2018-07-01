/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.danny.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    // This is a global variable [can be accessed by all methods in the code or app]
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_Field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants Chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // This indicates that only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(priceMessage);
        //Simpler alternative
        //displayMessage(createOrderSummary(price));

    }

    /**
     * Calculates the price of the order
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;


        // This code runs if the user opts for Whipped Cream
        // Add $1 if the user wants Whipped Cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // This code runs if the user opts for Chocolate
        // Add $2 if the user wants Chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;

    }

    /**
     * Create summary of the order.
     *
     * @param name            of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream; //addWhippedCream is a boolean variable, it only returns "true" or "false"
        priceMessage += "\nAdd chocolate? " + addChocolate; // addChocolate is a boolean variable, it only returns "true" or "false"
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /*
    /**
     * This method displays the given quantity value on the screen.
     */

    /*
    public void displayMessage(String message) {
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
    } */


    public void increment(View view) {
        // Sets upper bound to 100
        if (quantity == 100) {
            // Show an error message as toast
            Toast.makeText(this, "Order limit reached", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        // Sets lower bound to 1
        if (quantity == 1) {
            // Show an error message as toast
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

        /* This is an alternative solution
        if (quantity > 0) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            displayQuantity(0);
        }*/


    }


}