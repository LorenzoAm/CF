package com.example.codicefiscale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codicefiscale.databinding.ActivityMainBinding
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener{
            if(binding.name.text.toString()=="")
            {
                binding.cf.setText(R.string.error1)
            }
            else if(binding.surname.text.toString()==""){
                binding.cf.setText(R.string.error2)
            }
            else if(binding.date.text.toString()=="")
            {
                binding.cf.setText(R.string.error3)
            }
            else if(binding.gender.checkedRadioButtonId == -1)
            {
                binding.cf.setText(R.string.error4)
            }
            else if(binding.birthplace.text.toString()=="")
            {
                binding.cf.setText(R.string.error5)
            }
            else
            {
                val output : String
                val surname = binding.surname.text.toString().toUpperCase(Locale.ROOT)
                val name = binding.name.text.toString().toUpperCase(Locale.ROOT)
                val date = Pattern.compile("/").split(binding.date.text.toString())  //date is an array of string
                val birthplace = binding.birthplace.text.toString().toUpperCase(Locale.ROOT)
                val gender : String = if(binding.gender.checkedRadioButtonId == R.id.male)
                {
                    "M"
                } else
                {
                    "F"
                }

                output = calculateSurname(surname) + calculateName(name) + date[0][3-4]



                binding.cf.text = output


            }
        }
    }

    private fun calculateSurname(surname : String) : String
    {
        var output = ""
        var cont = 0
        while((cont < surname.length) && (output.length<3))
        {
            when(surname[cont])
            {
                'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z' -> output += surname[cont]
            }
            cont++
        }
        if(output.length<3)
        {
            cont = 0
            while((cont < surname.length) && (output.length<3))
            {
                when(surname[cont])
                {
                    'A','E','I','O','U' -> output += surname[cont]
                }
                cont++
            }
        }
        while(output.length<3)
        {
            output += 'X'
        }

        return output
    }
    
    private fun calculateName(name : String) : String
    {
        var cons = 0
        var cont = 0
        var out = ""
        var flag: Int

        while((cons < 4)&&(cont < name.length))
        {
            when(name[cont])
            {
                'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z' -> cons++
            }
            cont++
        }
        if(cons >= 4)
        {
            cont = 0
            cons = 0
            while(out.length < 3)
            {
                flag = when(name[cont]) {
                    'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z' -> 1
                    else -> 0
                }
                if(flag == 1)
                {
                    cons++
                    if(cons != 2)
                        out += name[cont]
                }
                cont++
            }
        }
        else
        {
            out = calculateSurname(name)
        }

        return out

    }



}