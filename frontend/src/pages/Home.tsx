import { Button } from "@/components/ui/button";
import { Helmet } from "react-helmet-async";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <>
      <Helmet>
        <title>Home</title>
        <meta name="description" content="Página inicial" />
        <meta name="keywords" content="home, autenticação, auth" />
      </Helmet>
      <div className="h-full flex flex-col mt-58 mb-58">
        <div className="flex-1 overflow-y-auto p-4">
          <div className="h-full bg-background rounded-lg shadow-lg p-4">
            <h1 className="text-center text-2xl">Página inicial</h1>
            <div className="flex justify-center mt-8">
              <Link to="/signin">
                <Button>Login</Button>
              </Link>
            </div>
            <div className="flex justify-center mt-4">
              <Link to="/signup">
                <Button>Registrar</Button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
