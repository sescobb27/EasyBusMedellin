package com.android.easybus.medellin.movil.listeners;


import java.util.LinkedList;

import android.content.DialogInterface;

public class SelectorRutasListener implements DialogInterface.OnClickListener{
	private LinkedList<SelectorListener> listListeners;
	
	public SelectorRutasListener() {
		listListeners = new LinkedList<SelectorListener>();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		for(SelectorListener listener : listListeners)
		{
			listener.responseSelectorObtained(which);
		}
	}
	
	public void subscribirse(SelectorListener listener)
	{
		listListeners.add(listener);
	}
	
	public void reset()
	{
		listListeners.clear();
	}
}
