package com.example.myfirstaplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Variables del juego
    private var numeroAleatorio = 0
    private var intentosRestantes = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos de la interfaz
        val tvFeedback = findViewById<TextView>(R.id.tv_feedback)
        val etGuess = findViewById<EditText>(R.id.et_guess)
        val btnGuess = findViewById<Button>(R.id.btn_guess)
        val tvAttempts = findViewById<TextView>(R.id.tv_attempts)
        val btnReset = findViewById<Button>(R.id.btn_reset)

        // Inicializar juego
        iniciarJuego()

        // Botón Adivinar
        btnGuess.setOnClickListener {
            val intento = etGuess.text.toString()

            if (intento.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numeroIntento = intento.toInt()
            procesarIntento(numeroIntento, tvFeedback, tvAttempts)
            etGuess.text.clear() // Limpiar el campo de texto
        }

        // Botón Reiniciar
        btnReset.setOnClickListener {
            iniciarJuego()
            tvFeedback.text = "¡Adivina un número entre 1 y 100!"
            tvAttempts.text = "Intentos restantes: 10"
            etGuess.text.clear()
        }
    }

    // Método para inicializar el juego
    private fun iniciarJuego() {
        numeroAleatorio = Random.nextInt(1, 101) // Generar número aleatorio entre 1 y 100
        intentosRestantes = 10
    }

    // Método para procesar los intentos del usuario
    private fun procesarIntento(numeroIntento: Int, tvFeedback: TextView, tvAttempts: TextView) {
        if (numeroIntento == numeroAleatorio) {
            tvFeedback.text = "¡Correcto! Has adivinado el número."
            bloquearJuego()
        } else {
            intentosRestantes--

            if (intentosRestantes <= 0) {
                tvFeedback.text = "Has perdido. El número era: $numeroAleatorio."
                bloquearJuego()
            } else {
                if (numeroIntento > numeroAleatorio) {
                    tvFeedback.text = "Demasiado alto. Intenta con un número más bajo."
                } else {
                    tvFeedback.text = "Demasiado bajo. Intenta con un número más alto."
                }
                tvAttempts.text = "Intentos restantes: $intentosRestantes"
            }
        }
    }

    // Método para bloquear el juego una vez terminado
    private fun bloquearJuego() {
        findViewById<Button>(R.id.btn_guess).isEnabled = false
        findViewById<EditText>(R.id.et_guess).isEnabled = false
    }
}
