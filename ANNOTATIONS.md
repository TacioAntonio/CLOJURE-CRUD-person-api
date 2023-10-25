# Rodando projeto clojure
Navegue até a raiz do projeto
Use o comando clj
(require 'app.index) - pasta
    -  :paths ["src"]: Aqui foi indicado onde se encontra o código-fonte, a partir dai,
        o require 'app.index, irá procurar e carregar o namespace, que representa o
        módulo do projeto
(app.index/start) - namespace
    - Irá carregar a função start dentro do namespace app