package com.ff.terminal.print;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 * @author hkansagr
 * 
 */
public class PrintJobWatcher {

	/** The flag for print job done. */
	boolean done = false;

	/**
	 * @param job
	 */
	PrintJobWatcher(DocPrintJob job) {
		System.out.println("PrintJobWatcher :: PrintJobWatcher() :: START");
		job.addPrintJobListener(new PrintJobAdapter() {
			public void printJobCanceled(PrintJobEvent pje) {
				allDone();
			}

			public void printJobCompleted(PrintJobEvent pje) {
				allDone();
			}

			public void printJobFailed(PrintJobEvent pje) {
				allDone();
			}

			public void printJobNoMoreEvents(PrintJobEvent pje) {
				allDone();
			}

			void allDone() {
				synchronized (PrintJobWatcher.this) {
					done = true;
					System.out
							.println("PrintJobWatcher :: allDone() :: Printing Done..!");
					PrintJobWatcher.this.notify();
				}
			}
		});
		System.out.println("PrintJobWatcher :: PrintJobWatcher() :: END");
	}

	/**
	 * To wait for done print job
	 */
	public synchronized void waitForDone() {
		System.out.println("PrintJobWatcher :: waitForDone() :: START");
		try {
			while (!done) {
				wait();
			}
		} catch (InterruptedException e) {
			System.out
					.println("Exception occurs in PrintJobWatcher :: waitForDone() :");
			e.printStackTrace();
		}
		System.out.println("PrintJobWatcher :: waitForDone() :: START");
	}

}
