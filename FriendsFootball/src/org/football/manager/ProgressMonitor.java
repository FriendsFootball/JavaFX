/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.football.manager;

import com.jcraft.jsch.SftpProgressMonitor;

// Change the class name if you want
public class ProgressMonitor implements SftpProgressMonitor {

    NewUserController newUser;

    public ProgressMonitor(NewUserController newUser) {
        this.newUser = newUser;
    }

    private long max = 0;
    private long count = 0;
    private long percent = 0;

    // If you need send something to the constructor, change this method
    public ProgressMonitor() {
    }

    public void init(int op, java.lang.String src, java.lang.String dest, long max) {
        this.max = max;
        System.out.println("Starting");
        //newUser.updateProgressBar(0);

        //src - Origin destination
        //dest - Destination path
        //max - Total filesize
    }

    public boolean count(long bytes) {
        this.count += bytes;
        long percentNow = this.count * 100 / max;
        if (percentNow > this.percent) {
            this.percent = percentNow;
            System.out.println(this.percent); // Progress 0,0
            //newUser.updateProgressBar(this.percent);
        }

        return (true);
    }

    public void end() {
        System.out.println("Finished");// The process is over
    }
}
