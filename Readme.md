to run jmh test
    
    sbt> jmh:run t1 -f 1 -wi 10 -i 10 .*Metrics.*
    
    
where:
       
    
    -wi <int>       Number of warmup iterations to do. Warmup iterations
                    are not counted towards the benchmark score.
    -i <int>        Number of measurement iterations to do. Measurement
                    iterations are counted towards the benchmark score.