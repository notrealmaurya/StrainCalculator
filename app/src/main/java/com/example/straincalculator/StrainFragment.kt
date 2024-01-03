package com.example.straincalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.straincalculator.databinding.FragmentStrainBinding


class StrainFragment : Fragment() {


    private lateinit var strainBinding: FragmentStrainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        strainBinding = FragmentStrainBinding.inflate(inflater, container, false)
        val view = strainBinding.root


        strainBinding.calculateButton.setOnClickListener {
            calculateStrains(strainBinding)
        }

        strainBinding.resetTextView.setOnClickListener {
            resetValues(strainBinding)
        }

        return view
    }


    private fun resetValues(strainBinding: FragmentStrainBinding) {
        strainBinding.sigmaXEditText.text.clear()
        strainBinding.sigmaYEditText.text.clear()
        strainBinding.sigmaZEditText.text.clear()
        strainBinding.elasticModulusEditText.text.clear()
        strainBinding.poissonRatioEditText.text.clear()
        strainBinding.resultTextView.text = ""

        // You can also add additional reset logic as needed
    }

    private fun calculateStrains(strainBinding: FragmentStrainBinding) {
        val sigmaXText = strainBinding.sigmaXEditText.text.toString()
        val sigmaYText = strainBinding.sigmaYEditText.text.toString()
        val sigmaZText = strainBinding.sigmaZEditText.text.toString()
        val elasticModulusText = strainBinding.elasticModulusEditText.text.toString()
        val poissonRatioText = strainBinding.poissonRatioEditText.text.toString()

        // Check if any of the input fields are empty
        if (sigmaXText.isEmpty() || sigmaYText.isEmpty() || sigmaZText.isEmpty() ||
            elasticModulusText.isEmpty() || poissonRatioText.isEmpty()
        ) {
            strainBinding.resultTextView.text = "Please enter values for all fields"
            return
        }

        // Convert the input values to Double
        val sigmaX = sigmaXText.toDouble()
        val sigmaY = sigmaYText.toDouble()
        val sigmaZ = sigmaZText.toDouble()
        val elasticModulus = elasticModulusText.toDouble()
        val poissonRatio = poissonRatioText.toDouble()

        // Calculate strains using Hooke's Law
        val epsilonX = (sigmaX / elasticModulus) -
                (poissonRatio * sigmaY / elasticModulus) -
                (poissonRatio * sigmaZ / elasticModulus)
        val epsilonY = sigmaY / elasticModulus -
                (poissonRatio * sigmaX / elasticModulus) -
                (poissonRatio * sigmaZ / elasticModulus)
        val epsilonZ = sigmaZ / elasticModulus -
                (poissonRatio * sigmaY / elasticModulus) -
                (poissonRatio * sigmaX / elasticModulus)

        // Display the results
        val resultText = "Strain (Ex): $epsilonX\nStrain (Ey): $epsilonY\nStrain (Ez): $epsilonZ"
        strainBinding.resultTextView.text = resultText
    }

}