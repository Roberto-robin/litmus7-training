package com.litmus7.java_emp_mgt.response;

public class StatusCode<A , B> {
        public final A first;
        public final B second;

        public StatusCode(A first , B second )
        {
            this.first = first;
            this.second =second;
        }
    
}
