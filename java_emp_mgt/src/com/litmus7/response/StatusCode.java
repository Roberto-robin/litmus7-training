package com.litmus7.response;

public class StatusCode<A , B> {
        public final A first;
        public final B second;

        public StatusCode(A first , B second )
        {
            this.first = first;
            this.second =second;
        }
    
}
