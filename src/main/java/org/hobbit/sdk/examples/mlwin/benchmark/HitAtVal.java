package org.hobbit.sdk.examples.mlwin.benchmark;

public class HitAtVal {
    public int hitForHeaD = 0;
    public int hitForTail = 0;
    int val;
	public HitAtVal(int val) {
		super();
		this.val = val;
	}
    public double increasetHitHead(int hitHead ) {
    	if (hitHead<=val)
    		hitForHeaD++; 		
    	return 0;
    }
    public double increasetHitTail(int hitTail ) {
    	if (hitTail<=val)
    		hitForTail++; 		
    	return 0;
    }
    public double getAccuracyHead(int nrData) {
    	return (double)hitForHeaD/nrData;
    }
    public double getAccuracyTail(int nrData) { return (double)hitForTail/nrData; }
	public int getHitForHeaD() {
		return hitForHeaD;
	}
	public void setHitForHeaD(int hitForHeaD) {
		this.hitForHeaD = hitForHeaD;
	}
	public int getHitForTail() {
		return hitForTail;
	}
	public void setHitForTail(int hitForTail) {
		this.hitForTail = hitForTail;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
}
