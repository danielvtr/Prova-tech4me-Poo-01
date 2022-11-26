//Código feito e adaptado por Daniel Vitor 

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Classe.Aeronave;
import Classe.Pessoa;
import Classe.Piloto;

public class AppFAB {
    public static void main(String[] args) throws InterruptedException, IOException {
        final int MAX_ELEMENTOS = 20;
        int opcao, qtdCadastrados = 0, index = 0 /* faz o loop*/;
        int EspacoPadrao = MAX_ELEMENTOS;
        int EspacoDisponivel = EspacoPadrao;
        Boolean parar = true; // trava pro loop
        String regex = "[0-9]+"; // regex para permitir apenas digitos

        Pessoa[] pilotos = new Pessoa[MAX_ELEMENTOS];
        Pattern pa = Pattern.compile(regex);
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("\n****\nMENU\n****\n");
            System.out.println("1 - Cadastrar piloto");
            System.out.println("2 - Listar pilotos cadastrados");
            System.out.println("3 - Localizar piloto pelo CPF");
            System.out.println("4 - Aumentar espaço de armazenamento");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = in.nextInt();
            in.nextLine();

            // Cadastrar piloto
            if (opcao == 1) {
                
                if (EspacoDisponivel <= 0) {
                    System.out.println("\nNão há espaço para cadastrar novos pilotos.");
                    voltarMenu(in);
                    continue;
                } else if (qtdCadastrados > MAX_ELEMENTOS) {
                    System.out.println("\nNão há espaço para cadastrar novos pilotos.");
                    voltarMenu(in);
                    continue;
                }
                // por causa dos loops tive que fazer desse jeito.
                String n, c, b, m, am, ac, res;
                do {
                    System.out.println("\nNome(Max 30 caracteres)");
                    n = in.nextLine();
                    if (n.length() <= 0 || n.length() > 30) {
                        System.out.println("\nNome invalido!!");
                    } else {
                        break;
                    }
                } while (parar);
                do {
                    System.out.println("\nCpf apenas digitos(11)");
                    c = in.nextLine();
                    Matcher ma = pa.matcher(c);
                    if (ma.matches() && c.length() == 11) {
                        break;
                    } else {
                        System.out.println("\nCpf invalido!!");
                    }
                } while (parar);
                do {
                    System.out.println("\nBrevê");
                    b = in.nextLine();
                    if (b.length() < 1) {
                        System.out.println("\nBrevê invalido!!");
                    } else {
                        break;
                    }
                } while (parar);
                do {
                    System.out.println("\nMatricula");
                    m = in.nextLine();
                    if (m.length() < 1) {
                        System.out.println("\nMatricula invalido!!");
                    } else {
                        break;
                    }
                } while (parar);
                String qtdDeAvioesString;
                int qtdDeAvioes = 0;
                Boolean tudoCerto = false;
                do {
                    System.out.println("\nquer cadastrar aviões?(s ou n)");
                    res = in.nextLine();
                    if (res.equals("s")) {
                        do {
                            System.out.println("\nQuantos aviões quer cadastrar para este piloto?(max: 50)");
                            qtdDeAvioesString = in.nextLine();
                            Matcher ma = pa.matcher(qtdDeAvioesString);
                            if (qtdDeAvioesString.length() > 0) {
                                if (ma.matches()) {
                                    qtdDeAvioes = Integer.parseInt(qtdDeAvioesString);
                                    if (qtdDeAvioes < 51) {
                                        tudoCerto = true;
                                        break;
                                    } else {
                                        System.out.println("\nQuantidade invalida!!: O valor deve ser no maximo 50");
                                    }
                                } else {
                                    System.out.println("\nQuantidade invalida!!: O valor deve conter apenas digitos");
                                }
                            } else {
                                System.out.println("\nQuantidade invalida!!: Por favor digite um valor");
                            }
                        } while (parar);
                    } else if (res.equals("n")) {
                        break;
                    } else {
                        System.out.println("\nOpção invalida!!(use 's' ou 'n')");
                    }
                } while (!tudoCerto);
                if (qtdDeAvioes > 0) {
                    Pessoa p1 = new Piloto(n, c, b, m);
                    while (index != qtdDeAvioes) {
                        Aeronave a1 = new Aeronave();
                        System.out.println("\nInformações do avião: " + (index + 1));
                        do {
                            System.out.println("\nModelo do avião");
                            am = in.nextLine();

                            if (am.length() < 1) {
                                System.out.println("\nModelo invalido!!");
                            } else {
                                a1.setModelo(am);
                                p1.getPiloto().setAviao(a1, index);
                                break;
                            }
                        } while (parar);
                        do {
                            System.out.println("\nCategoria do avião");
                            ac = in.nextLine();

                            if (ac.length() < 1) {
                                System.out.println("\nCategoria invalido!!");
                            } else {
                                a1.setCategoria(ac);
                                p1.getPiloto().setAviao(a1, index);
                                break;
                            }
                        } while (parar);
                        p1.getPiloto().setAumentarQtdAviao();
                        index++;
                    }
                    pilotos[qtdCadastrados] = p1;
                    index = 0;
                } else {
                    Pessoa p1 = new Piloto(n, c, b, m);
                    pilotos[qtdCadastrados] = p1;
                }
                qtdCadastrados++;
                EspacoDisponivel--;
                System.out.println("\nPiloto cadastrado com sucesso.");
                voltarMenu(in);
            }
            if (opcao == 2) {
                int indexAviao = 0;
                System.out.println("\nPilotos cadastrados atualmente: " + qtdCadastrados);
                if (qtdCadastrados == 0) {
                    System.out.println("\nNão há pilotos cadastrados para exibir.");
                    voltarMenu(in);
                    continue;
                }
                while (index != qtdCadastrados) {

                    System.out.printf(
                            "\n\nPiloto %s:||, nome: %s, cpf: %s, breve: %s, matricula: %s.",
                            (index + 1),
                            pilotos[index].getNome(), pilotos[index].getCpf(),
                            pilotos[index].getPiloto().getBreve(), pilotos[index].getPiloto().getMatricula());
                    if (pilotos[index].getPiloto().checarSeTemAviao()) {
                        System.out.println("\nAviões do piloto:");
                        // mostra os avioes do piloto
                        while (indexAviao != pilotos[index].getPiloto().getQtdDeAvioesEspecifica()) {
                            System.out.printf("\nAvião %s|| modelo: %s categoria: %s", (indexAviao + 1),
                                    pilotos[index].getPiloto().getAviao(indexAviao).getModelo(),
                                    pilotos[index].getPiloto().getAviao(indexAviao).getCategoria());
                            indexAviao++;
                        }
                        indexAviao = 0;
                    }
                    System.out.println("\n--------------------------------------------------------------------------");
                    index++;
                }

                index = 0;
                voltarMenu(in);
            }
            else if (opcao == 3) {
                String CpfProcurado;
                int PilotoProcurado = 0;
                int indexAviao = 0;

                do {
                    System.out.println("\nQual cpf deseja encontrar?(11 digitos)");
                    CpfProcurado = in.nextLine();
                    Matcher ma = pa.matcher(CpfProcurado);
                    if (ma.matches() && CpfProcurado.length() == 11) {
                        break;
                    } else {
                        System.out.println("\nCpf invalido!!");
                    }
                } while (parar);

                while (index != qtdCadastrados) {
                    if (pilotos[index].getCpf().equals(CpfProcurado)) {
                        PilotoProcurado = index + 1;
                        index = qtdCadastrados;
                    } else {
                        index++;
                    }
                }
                index = 0;
                if (PilotoProcurado == 0) {
                    System.out.println("\nCpf não encontrado");
                    voltarMenu(in);
                } else {
                    System.out.printf(
                            "\nEste cpf pertence ao piloto: %s|| nome: %s cpf: %s breve: %s matricula: %s",
                            (PilotoProcurado), pilotos[PilotoProcurado - 1].getNome(),
                            pilotos[PilotoProcurado - 1].getCpf(),
                            pilotos[PilotoProcurado - 1].getPiloto().getBreve(),
                            pilotos[PilotoProcurado - 1].getPiloto().getMatricula());

                    if (pilotos[PilotoProcurado - 1].getPiloto().checarSeTemAviao()) {
                        System.out.println("\nAviões do piloto:");
                        while (indexAviao != pilotos[PilotoProcurado - 1].getPiloto().getQtdDeAvioesEspecifica()) {
                            System.out.printf("\nAvião %s|| modelo: %s categoria: %s", (indexAviao + 1),
                                    pilotos[PilotoProcurado - 1].getPiloto().getAviao(indexAviao).getModelo(),
                                    pilotos[PilotoProcurado - 1].getPiloto().getAviao(indexAviao).getCategoria());
                            indexAviao++;
                        }
                        indexAviao = 0;
                    }
                    voltarMenu(in);
                }

            }

            else if (opcao == 4) {
                int NovoEspaço = 0;
                System.out.printf("\nQuantidade cadastrada: %s\nEspaço maximo: %s", qtdCadastrados, EspacoPadrao);
                System.out.println("\n--------------------------------------");
                System.out.println("Para quanto de espaço deseja aumentar?");
                NovoEspaço = in.nextInt();
                in.nextLine();
                if (NovoEspaço > EspacoPadrao) {

                    EspacoPadrao = NovoEspaço;
                    EspacoDisponivel = EspacoPadrao - qtdCadastrados;

                    Pessoa[] ArrayTemporario = new Pessoa[pilotos.length];
                    while (index != qtdCadastrados) {
                        ArrayTemporario[index] = pilotos[index];
                        index++;
                    }
                    index = 0;

                    pilotos = new Pessoa[NovoEspaço];
                    while (index != qtdCadastrados) {
                        pilotos[index] = ArrayTemporario[index];
                        index++;
                    }
                    index = 0;
                    System.out.println("\nEspaço modificado com sucesso.");
                    voltarMenu(in);
                } else {
                    System.out.println("\nNão é possivel baixar o espaço disponivel");
                    voltarMenu(in);
                }

            } else if (opcao != 0) {
                System.out.println("\nOpção inválida!");
            }
        }
        while (opcao != 0);

        System.out.println("Fim do programa!");

        in.close();
    }

    private static void voltarMenu(Scanner in) throws InterruptedException, IOException {
        System.out.println("\n\nPressione ENTER para voltar ao menu.");
        in.nextLine();
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");

        System.out.flush();

    }
}