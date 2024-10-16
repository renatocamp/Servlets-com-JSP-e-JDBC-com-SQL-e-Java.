package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.LoginDAORepository;
import Model.ModelLogin;

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"}) /* Mapeamento de Dados que vem da Tela */
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private LoginDAORepository repository = new LoginDAORepository();

	public ServletLogin() {
		super();
	}

	/* Recebe os dados enviados por uma URL por paramêtros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		// INVALIDA A SESSÃO
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate(); 
			
			RequestDispatcher redirect = request.getRequestDispatcher("index.jsp");
			redirect.forward(request, response);
		
		}else {
			doPost(request, response);
		}

	}

	/* Recebe os dados enviados por um formulário */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				if (repository.isAuthenticated(modelLogin)) {

					/* Cria uma seção para deixar o usuário logado */
					request.getSession().setAttribute("usuarioLogado",
							modelLogin.getLogin()); 

					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}

					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "INFORME O LOGIN E SENHA CORRETAMENTE!");
					redirecionar.forward(request, response);
				}

			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "INFORME O LOGIN E SENHA CORRETAMENTE!");
				redirecionar.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
