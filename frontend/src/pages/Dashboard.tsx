import { Helmet } from "react-helmet-async";
import { EmailConfirmation } from "@/components/EmailConfirmation";
import { useAuth } from "@/hooks/useAuth";

export default function Dashboard() {
  const { user } = useAuth();

  return (
    <>
      <Helmet>
        <title>Dashboard</title>
        <meta name="description" content="Página de dashboard" />
        <meta name="keywords" content="dashboard, autenticação, auth" />
      </Helmet>
      <div className="h-full flex flex-col mt-58 mb-58">
        <div className="flex-1 overflow-y-auto p-4">
          {user && !user.isActive ? (
            <EmailConfirmation userId={user.id} />
          ) : (
            <div className="h-full bg-background rounded-lg shadow-lg p-4">
              <h1 className="mx-auto text-center text-2xl">Dashboard</h1>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
