package edu.vt.cs5254.multiquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Name: Nikilesh Madala
    //PID: nikileshm

    private lateinit var binding: ActivityMainBinding

    private val answers = mutableListOf(
        Answer(R.string.answer_0, isCorrect = false),
        Answer(R.string.answer_1, isCorrect = true),
        Answer(R.string.answer_2, isCorrect = false),
        Answer(R.string.answer_3, isCorrect = false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.answer0Button.setOnClickListener { toggleAnswer(0) }
        binding.answer1Button.setOnClickListener { toggleAnswer(1) }
        binding.answer2Button.setOnClickListener { toggleAnswer(2) }
        binding.answer3Button.setOnClickListener { toggleAnswer(3) }

        binding.fiftyFiftyButton.setOnClickListener { disableTwoIncorrectAnswers() }
        binding.resetButton.setOnClickListener { resetAnswers() }

        updateView()
    }

    private fun toggleAnswer(index: Int) {
        answers.forEachIndexed { i, answer ->
            answer.isSelected = i == index && !answer.isSelected
        }
        updateView()
    }

    private fun disableTwoIncorrectAnswers() {
        val correctAnswerIndex = answers.indexOfFirst { it.isCorrect }
        val disabledAnswers = answers
            .filter { !it.isCorrect && it.isEnabled }
            .take(2)

        disabledAnswers.zip(disabledAnswers.indices).forEach { (answer, index) ->
            answer.isEnabled = false
            answer.isSelected = false
        }

        updateView()
    }

    private fun resetAnswers() {
        answers.forEach { it.isEnabled = true; it.isSelected = false }
        updateView()
    }

    private fun updateView() {
        answers.forEachIndexed { index, answer ->
            val button = when (index) {
                0 -> binding.answer0Button
                1 -> binding.answer1Button
                2 -> binding.answer2Button
                3 -> binding.answer3Button
                else -> return@forEachIndexed
            }
            button.isEnabled = answer.isEnabled
            button.isSelected = answer.isSelected
            button.setText(answer.textResId)
            button.updateColor()
        }

        val hasDisabledAnswers = answers.filter { !it.isCorrect }.any { !it.isEnabled }
        binding.fiftyFiftyButton.isEnabled = !hasDisabledAnswers
    }
}
