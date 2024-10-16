package Filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Connection.SingleConnectionDB;

@WebFilter(urlPatterns = {"/principal/*"}) // Intercepta todas as requisições que vêm do projeto ou mapeamento
public class FilterAutenticacao extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	private static Connection connection;

	public FilterAutenticacao() {
		super();

	}

	public void destroy() { // ENCERRA OS PROCESSOS QUANDO O SERVIDOR É PARADO
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * INTERCEPTA TUDO QUE VEM DO SISTEMA, REQUESTS E RESPONSES TUDO QUE FIZER NO
	 * SISTEMA SERÁ FEITO POR AQUI VALIDAÇÃO DE AUTENTICAÇÃO DAR COMMIT E ROLLBACK
	 * DE TRANSAÇÕES NO BANCO
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			HttpServletRequest req = (HttpServletRequest) request;

			HttpSession session = req.getSession();

			String usuario = (String) session.getAttribute("usuarioLogado");

			// URL QUE ESTÁ SENDO ACESSADAs
			String authenticationURL = req.getServletPath(); 

			// VALIDAR SE ESTÁ LOGADO, CASO CONTRÁRIO REDIRECIONAR PARA TELA LOGIN

			if (usuario == null && !authenticationURL.equalsIgnoreCase("/princiapl/ServletLogin")) {

				RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url=" + authenticationURL);

				request.setAttribute("msg", "POR FAVOR, FAÇA SEU LOGIN");

				redirect.forward(request, response);

				// PARA A EXECUÇÃO E REDIRECIONA PARA O LOGIN
				return; 

			} else {
				
				chain.doFilter(request, response);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();

			}
			
			HttpServletResponse res = (HttpServletResponse) response;
			if (!res.isCommitted()) {
				RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}
		}

	}
	
	// INICIA OS PROCESSOS OU RECURSOS QUANDO O
	// SERVIDOR SOBE O PROJETO
	public void init(FilterConfig fConfig) throws ServletException { 
		connection = SingleConnectionDB.getConnection();
	}

}
