import sys

def main():
    while True:
        # Lê a entrada do Java
        command = sys.stdin.readline().strip()

        if not command:
            continue

        # Encerra o loop ao receber "sair"
        if command.lower() == "sair":
            print("Encerrando comunicacao!", flush=True)
            break

        # Processa o comando recebido
        response = f"Recebido: {command}"
        print(response, flush=True)  # Envia a resposta ao Java

if __name__ == "__main__":
    main()
