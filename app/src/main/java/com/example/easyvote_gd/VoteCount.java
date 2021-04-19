package com.example.easyvote_gd;

import android.widget.TextView;

public class VoteCount {

    public VoteCount(TextView name) {
    }

    private int voteCount;

    public VoteCount(int voteCount) {

        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
