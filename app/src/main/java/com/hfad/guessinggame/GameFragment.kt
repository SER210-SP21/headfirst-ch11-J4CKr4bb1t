package com.hfad.guessinggame
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.guessinggame.databinding.FragmentGameBinding
import androidx.navigation.findNavController
import androidx.lifecycle.Observer


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    lateinit var viewModel: GameViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //updateScreen()
//        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
//        })
//        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.lives.text = "You have $newValue lives left"
//        })
//
//        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
//            binding.word.text = newValue
//        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {

                val action = GameFragmentDirections
                        .actionGameFragmentToResultFragment(viewModel.wonLostMessage())

                view.findNavController().navigate(action)
            }
        })



        binding.guessButton.setOnClickListener() {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            //updateScreen()

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
