package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Sale> sale = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				sale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio: ");

			Comparator<Double> comp = (S1, S2) -> S1.compareTo(S2);
			
			List <Double> avg = sale.stream()
                    .filter(s -> s.getYear() == 2016)
                    .map(s -> s.averagePrice())
                    .sorted(comp.reversed())
                    .limit(5)
                    .collect(Collectors.toList());
			
			avg.forEach(System.out::println);
			
			
			double sum = sale.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + sum);

		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();
	}
}
