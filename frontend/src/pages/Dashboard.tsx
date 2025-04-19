import { Helmet } from "react-helmet-async";
import { EmailConfirmation } from "@/components/EmailConfirmation";
import { useAuth } from "@/providers/AuthContext";
import { Loader2 } from "lucide-react";

export default function Dashboard() {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return (
      <div className="flex h-screen items-center justify-center">
        <Loader2 className="animate-spin" size={48} />
      </div>
    );
  }

  return (
    <>
      <Helmet>
        <title>Dashboard</title>
        <meta name="description" content="Página de dashboard" />
        <meta name="keywords" content="dashboard, autenticação, auth" />
      </Helmet>
      <div className="flex items-center justify-center h-screen">
        <div className="flex-1 overflow-y-auto">
          {user && !user.isActive ? (
            <EmailConfirmation userId={user.id} />
          ) : (
            <div className="h-full">
              <h1 className="mx-auto text-center text-2xl">Dashboard</h1>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
