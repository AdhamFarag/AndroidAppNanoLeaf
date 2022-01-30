package com.adhamfarag.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        ASSUMPTIONS MADE: 1- Never asked user for i value because it was i=j*k if the user did not
                            match these numbers up then there would have been invalid inputs
                            so I just asked for j and k which are the number of lightbulb colours
                            and the quantity of each lightbulb colour respectively
                          2- Confidence Interval is as a percentage to remove this
                             then we remove the (100/n) that the CIleft and CIright are multiplied
                             by

         */

        fun convertToInt(str: String):Int {
            /*
            Helper function to convert Strings to int and also checking if they are empty first
            help to make default parameter which is 0 if the user never made an input
             */
            var n=0
            if(str != "") {
                n = Integer.parseInt(str)
            }
            return n
        }


        var bucket = mutableListOf<String>()

        fun createBucket(numOfLightbulbColor:Int ,
                         quantityOfEachLightbulbColor:Int): MutableList<String> {
            /*
            create a bucket of light bulbs for the user
             */
            for (color in 1..numOfLightbulbColor) {
                for (m in 1..quantityOfEachLightbulbColor) {
                    bucket.add(color.toString())
                }
            }
            return bucket
        }

        fun takeLightBulb(): String {
            /*
            Draw a light bulb from the bucket and return it
             */
            if (bucket.size!=0) {
                val randomIndex = Random.nextInt(0, bucket.size)
                return bucket.removeAt(randomIndex)
            }
            return ""
        }
        fun runSimulation(numOfLightbulbColor:Int,quantityOfEachLightbulbColor:Int
                          ,ballsToDraw:Int,numSimulations:Int) {
            /*
            driver function to run simulation
             */
            var success = 0
            // loop through the number of simulations
            for (e in 1..numSimulations){
                // create a different bucket because we remove the bulbs from the previous bucket
                bucket = createBucket(numOfLightbulbColor,quantityOfEachLightbulbColor)
                var balls = mutableSetOf<String>()
                // take a bulb from the bucket and add it to a set
                for (i in 1..ballsToDraw){
                    var color = takeLightBulb()
                    balls.add(color)
                }
                // if all the balls we drew are unique then we have a successful iteration
                if (balls.size == ballsToDraw){
                    success+=1
                }
                bucket.clear();
            }
            var result =(success.toDouble()/numSimulations.toDouble())
            var mean = numSimulations * result
            var standardDeviation = kotlin.math.sqrt(numSimulations * result * (1 - result))
            println(standardDeviation)
            var CIleft = (mean-(2.58*standardDeviation))*100/numSimulations
            var CIright = (mean+(2.58*standardDeviation))*100/numSimulations
            println(CIleft.toString()+" "+CIright.toString());
            Probability.text = "Probability for this is "+"%.2f".format(result)
            Confidence.text = "CI is ["+"%.2f".format(CIleft)+", " + "%.2f".format(CIright) + "]"


        }

        runSimulationBtn.setOnClickListener(){
            // get all the input values
            var numOfLightbulbColor = convertToInt(NumberOfLightbulbColors.text.toString())
            var quantityOfEachLightbulbColor = convertToInt(QuantityOfEachLightbulbColors.text.toString());
            var ballsToDraw = convertToInt(NumberOfBallsToDraw.text.toString());
            var numSimulations = convertToInt(NumberOfSimulations.text.toString());
            // basic error checking in case user left a field blank
            if (numOfLightbulbColor==0) {
                Toast.makeText(
                    this,
                    "Hmm, Looks like you didn't input a number " +
                            "for the amount of light bulb colors",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else if (quantityOfEachLightbulbColor==0) {
                Toast.makeText(
                    this,
                    "Hmm, Looks like you didn't input a number " +
                            "for quantity of each light bulb color",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else if (ballsToDraw==0) {
                Toast.makeText(
                    this,
                    "Hmm, Looks like you didn't input a number of balls to draw from",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else if (numSimulations==0) {
                Toast.makeText(
                    this,
                    "Hmm, Looks like you didn't input a number of simulations",
                    Toast.LENGTH_SHORT
                ).show();
            }
            else{
                runSimulation(numOfLightbulbColor,quantityOfEachLightbulbColor,
                    ballsToDraw,numSimulations);
            }
        }
    }

    }


