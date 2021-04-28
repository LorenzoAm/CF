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

            //controllo campi vuoti
            if((binding.name.text.toString()=="")||(binding.surname.text.toString()=="")||(binding.date.text.toString()=="")||(binding.gender.checkedRadioButtonId == -1)||(binding.birthplace.text.toString()==""))
            {
                binding.cf.setText(R.string.error1)
            }
                    //controllo sintassi nome
            else if(!check(binding.name.text.toString()))
            {
                binding.cf.setText(R.string.error2)
            }
                    //controllo sintassi cognome
            else if(!check(binding.surname.text.toString()))
            {
                binding.cf.setText(R.string.error3)
            }
                    //controllo date format
            else if((binding.date.text.toString().length!=10)||(binding.date.text.toString().filter { it == '/' }.count()!=2)) //controllo su lettere
            {
                binding.cf.setText(R.string.error4)
            }
                    //controllo sintassi birthplace
            else if(!check(binding.birthplace.text.toString()))
            {
                binding.cf.setText(R.string.error5)
            }
            else
            {
                var output : String
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

                output = calculateSurname(surname) + calculateName(name) + date[2].substring(2,4) + calculateMonth(date[1]) + calculateDay(gender,date[0]) + calculateMunicipality(birthplace)
                output += control(output)

                binding.cf.text = output


            }
        }
    }

    private fun check(x : String) : Boolean
    {
        var out = true
        var cont = 0
        while(cont<x.length)
        {
            when(x[cont].toString())
            {
                "0","1","2","3","4","5","6","7","8","9" -> out = false
            }
            cont++
        }
        return out
    }

    private fun control(input: String): String
    {
        var output = 0
        var cont = 0
        while (cont<input.length)
        {
            if((cont+1)%2==0)    //carattere in posizione pari
            {
                when(input[cont].toString())
                {
                    "A","0" -> output += 0
                    "B","1" -> output += 1
                    "C","2" -> output += 2
                    "D","3" -> output += 3
                    "E","4" -> output += 4
                    "F","5" -> output += 5
                    "G","6" -> output += 6
                    "H","7" -> output += 7
                    "I","8" -> output += 8
                    "J","9" -> output += 9
                    "K" -> output += 10
                    "L" -> output += 11
                    "M" -> output += 12
                    "N" -> output += 13
                    "O" -> output += 14
                    "P" -> output += 15
                    "Q" -> output += 16
                    "R" -> output += 17
                    "S" -> output += 18
                    "T" -> output += 19
                    "U" -> output += 20
                    "V" -> output += 21
                    "W" -> output += 22
                    "X" -> output += 23
                    "Y" -> output += 24
                    "Z" -> output += 25
                }
            }
            else
            {
                when(input[cont].toString())
                {
                    "A","0" -> output += 1
                    "B","1" -> output += 0
                    "C","2" -> output += 5
                    "D","3" -> output += 7
                    "E","4" -> output += 9
                    "F","5" -> output += 13
                    "G","6" -> output += 15
                    "H","7" -> output += 17
                    "I","8" -> output += 19
                    "J","9" -> output += 21
                    "K" -> output += 2
                    "L" -> output += 4
                    "M" -> output += 18
                    "N" -> output += 20
                    "O" -> output += 11
                    "P" -> output += 3
                    "Q" -> output += 6
                    "R" -> output += 8
                    "S" -> output += 12
                    "T" -> output += 14
                    "U" -> output += 16
                    "V" -> output += 10
                    "W" -> output += 22
                    "X" -> output += 25
                    "Y" -> output += 24
                    "Z" -> output += 23
                }
            }
            cont++
        }

        return when(output%26)
        {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            4 -> "E"
            5 -> "F"
            6 -> "G"
            7 -> "H"
            8 -> "I"
            9 -> "J"
            10 -> "K"
            11 -> "L"
            12 -> "M"
            13 -> "N"
            14 -> "O"
            15 -> "P"
            16 -> "Q"
            17 -> "R"
            18 -> "S"
            19 -> "T"
            20 -> "U"
            21 -> "V"
            22 -> "W"
            23 -> "X"
            24 -> "Y"
            25 -> "Z"
            else -> ""

        }

    }

    private fun calculateMunicipality(municipality: String): String
    {
        return when(municipality)
        {
            "ROMA" -> "H501"
            "BOLOGNA" -> "A944"
            "L'AQUILA" -> "A345"
            "MILANO" -> "F205"
            "FIRENZE" -> "D612"
            else -> ""
        }
    }

    private fun calculateDay(gender: String, day: String): String
    {
        val output : String

        if(gender == "M")
        {
            output = if(day.length==1)
            {
                "0$day"
            }
            else
            {
                day
            }
        }
        else
        {
            output = if(day.length==1)
            {
                "4$day"
            }
            else
            {
                val temp = day.toInt() + 40
                temp.toString()
            }
        }

        return output
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
                    'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Y','Z' -> 1  //e' una consonante
                    else -> 0                   //e' una vocale
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

    private fun calculateMonth(month : String) : String
    {

        return when(month)
        {
            "01" -> "A"
            "02" -> "B"
            "03" -> "C"
            "04" -> "D"
            "05" -> "E"
            "06" -> "H"
            "07" -> "L"
            "08" -> "M"
            "09" -> "P"
            "10" -> "R"
            "11" -> "S"
            "12" -> "T"
             else -> ""

        }
    }








}