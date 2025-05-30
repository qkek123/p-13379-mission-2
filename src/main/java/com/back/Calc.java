package com.back;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Calc {
    public static int run(String input) {
        List<String> arr = new ArrayList<>(Arrays.asList(input.split(" ")));

            List<String> orderList = new ArrayList<>();
            StringBuilder sb = new StringBuilder(input);

        while (sb.indexOf("(") != -1) {
            int right = sb.indexOf(")");
            int left = sb.lastIndexOf("(", right);

            String middle = sb.substring(left + 1, right);
            middle = calculate(middle);

            sb.replace(left, right + 1, middle);
        }

        String rest = sb.toString().trim();
        if (!rest.isEmpty()) {
            orderList.add(rest);
        }

        String newMethod = orderList.stream()
                                .filter(Objects::nonNull)
                                .map(el -> el + " ")
                                .collect(Collectors.joining());

            arr = new ArrayList<>(Arrays.stream(newMethod.split(" ")).toList());

        System.out.println();
//       위까지 식 정렬하기. 아래는 사칙연산 시작.
            while (arr.contains("*")) {
                //곱하기 우선순위
                //만약 배열에 *가 포함되어있다면 *의 인덱스를 뽑아서 그 앞뒤를 곱하기
                int multiplyIndex = arr.indexOf("*");
                int multiplyAnswer = Integer.parseInt(arr.get(multiplyIndex - 1)) * Integer.parseInt(arr.get(multiplyIndex + 1));
                arr.set(multiplyIndex - 1, String.valueOf(multiplyAnswer));
                arr.remove(multiplyIndex);
                arr.remove(multiplyIndex);
            }

        int answer = parseInt(arr.get(0));

        for (int i = 0; i < arr.size(); i++) {
            String token = arr.get(i);
            if (token.equals("+")) {
                answer += Integer.parseInt(arr.get(i + 1));
                i++;
            } else if (token.equals("-")) {
                answer -= Integer.parseInt(arr.get(i + 1));
                i++;
            } else if (!token.equals("")) {
                answer = Integer.parseInt(token);
            }
        }

        return answer;
    }

    private static String calculate(String middle) {
        List<String> mtpArr = new ArrayList<>(Arrays.asList(middle.split(" ")));
        while (mtpArr.contains("*")) {
            int multiplyIndex = mtpArr.indexOf("*");
            int multiplyAnswer = Integer.parseInt(mtpArr.get(multiplyIndex - 1)) * Integer.parseInt(mtpArr.get(multiplyIndex + 1));
            mtpArr.set(multiplyIndex - 1, String.valueOf(multiplyAnswer));
            mtpArr.remove(multiplyIndex);
            mtpArr.remove(multiplyIndex);
        }
        int answer = parseInt(mtpArr.get(0));

        for (int i = 0; i < mtpArr.size(); i++) {
            if (mtpArr.get(i).equals("+")) {
                answer = answer + parseInt(mtpArr.get(i + 1));
            } else if (mtpArr.get(i).equals("-")) {
                answer = answer - parseInt(mtpArr.get(i + 1));
            } else if (mtpArr.get(i).contains("-")) {
                String[] tmpArr = mtpArr.get(i).split("-");
                answer = answer - Integer.parseInt(tmpArr[1]);
            }
        }

        return String.valueOf(answer);
        }

}
