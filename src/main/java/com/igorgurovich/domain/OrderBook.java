package com.igorgurovich.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorgurovich on 2/8/17.
 */
public class OrderBook {

    private long sequence;
    private List bids;
    private List asks;

    public OrderBook() {
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public List getBids() {
        return bids;
    }

    public void setBids(List bids) {
        this.bids = bids;
    }

    public List getAsks() {
        return asks;
    }

    public void setAsks(List asks) {
        this.asks = asks;
    }
}
