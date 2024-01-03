package com.example.straincalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.straincalculator.databinding.FragmentStrainEnergyBinding


class StrainEnergyFragment : Fragment() {

    private lateinit var binding: FragmentStrainEnergyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStrainEnergyBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.calculateButton.setOnClickListener {
            calculateStrainEnergy(binding)
        }

        binding.resetTextView.setOnClickListener {
            resetValues(binding)
        }

        return view
    }

    private fun resetValues(binding: FragmentStrainEnergyBinding) {
        binding.pressureEditText.text.clear()
        binding.lengthEditText.text.clear()
        binding.areaEditText.text.clear()
        binding.modulusEditText.text.clear()
        binding.resultTextView.text = ""

        // You can also add additional reset logic as needed
    }


    private fun calculateStrainEnergy(binding: FragmentStrainEnergyBinding) {

        val pressureText = binding.pressureEditText.text.toString()
        val lengthText = binding.lengthEditText.text.toString()
        val areaText = binding.areaEditText.text.toString()
        val modulusText = binding.modulusEditText.text.toString()

        // Check if any of the input fields are empty
        if (pressureText.isEmpty() || lengthText.isEmpty() || areaText.isEmpty() || modulusText.isEmpty()) {
            binding.resultTextView.text = "Please enter values for all fields"
            return
        }

        try {
            val pressure = pressureText.toDouble()
            val length = lengthText.toDouble()
            val area = areaText.toDouble()
            val modulus = modulusText.toDouble()
            // Calculate strain energy
            val strainEnergy = (pressure * pressure * length) / (2 * area * modulus)

            // Display the result
            val resultText = "Strain Energy (U): $strainEnergy"
            binding.resultTextView.text = resultText

        } catch (e: NumberFormatException) {
            binding.resultTextView.text = "Invalid input. Please try Again "
        }


    }
}

