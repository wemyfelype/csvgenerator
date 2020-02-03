package com.cuscuzmachine.batch.csvgenerator.utils;

// Author: Wemy Vieira Felipe (85) 987741858
// Copyright (c) Cuscuz Machine. All rights reserved.

public class Help {

    public static void show(){
        System.out.println("available commands: ");
        System.out.println("");
        System.out.println("-p|-P <OWNER.PROCEDURE_NAME([PARAMETERS])>    {storage procedure name with parameters}");
        System.out.println("-t|-T [yes|no]                                {put headers in csv file : default yes}");
        System.out.println("-f|-F <FILE_NAME)>                            {name of file : default owner.procedurename}");
        System.out.println("-d|-D <path_to_save_csv>                      {path to save csv file : default C:/Innovation/}");
    }

}