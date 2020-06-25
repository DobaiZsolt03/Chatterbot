package com.example.chatterbot.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chatterbot.Chatrooms.ArtsActivity;
import com.example.chatterbot.R;


public class ChatFragment extends Fragment {

    ImageView arts;
    ImageView bookworms;
    ImageView music;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        arts = view.findViewById(R.id.btn_arts);
        bookworms = view.findViewById(R.id.btn_bookworms);
        music = view.findViewById(R.id.btn_music);


        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArtsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}