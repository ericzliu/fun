#!/bin/bash

read N

# read and compute sum
sum=0
var=1
for (( ; ; ))
do
    read num
    sum=$((sum+num))
    var=$((var+1))
    if ((var > N)); then
        break
    fi
done

# get absolute value of sum
pos=1
if ((sum < 0)); then
    pos=0
    sum=$((-sum))
fi

# compute avg and round to three decimals
avg=`echo "$sum/$N+0.0005" | bc -l`
avg=`echo "scale=3; $avg/1" | bc`

# output sign if necessary
if ((pos==1)); then
    echo $avg
else
    echo "-$avg"
fi