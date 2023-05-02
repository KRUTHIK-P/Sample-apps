package com.example.musicapp

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class PlayerService : Service() {

    private lateinit var player: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(init: Intent, flag: Int, startId: Int): Int {
        val seekTo = init.extras?.getInt("seekTo")
        player = MediaPlayer.create(this, R.raw.song)

        if (seekTo != null) {
            player.seekTo(seekTo)
            player.start()
        }

        val audioFocusChangeListener =
            AudioManager.OnAudioFocusChangeListener { focusChange ->
                if(focusChange <= 0) {
                    //pause music
                    player?.pause()
                }
            }

        val audioManger = getSystemService(AUDIO_SERVICE) as AudioManager

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .build()

        val result = audioManger.requestAudioFocus(focusRequest)

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            player?.start();
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}