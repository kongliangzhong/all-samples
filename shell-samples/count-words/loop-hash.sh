#!/bin/sh

declare -a HashWord  ## define an array
File="words.txt"

function ReadTxtFile() {
    while read Line
    do
        Word=(${Line})
        ##echo ${Word[@]}
        for Var in ${Word[@]}
        do
            #echo ${Var}
            HashWord+=( [${Var}]='1')
            Word[$[Var]]=
            for i in ${Word[@]}
            do
                if [[ ${Var} == i ]]; then
                    Value=${HashWord[${Var}]}
                fi
            done
        done
    done < ${File}

    for key in ${!HashWord[*]}
    do
        echo "${key}"
    done

}

## Main Entry:
ReadTxtFile
