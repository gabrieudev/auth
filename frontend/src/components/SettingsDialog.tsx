import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { ThemeToggle } from "./ThemeToggle";
import { useState, useEffect } from "react";
import { User } from "@/types/user";
import { getMe, forgotPassword } from "@/services/userService";
import { Button } from "./ui/button";
import { toast } from "sonner";
import { AxiosError } from "axios";

export function SettingsDialog({
  open,
  onOpenChange,
}: {
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
}) {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      await getMe()
        .then((data) => {
          setUser(data);
        })
        .catch((error) => {
          console.error(error);
        });
    };
    fetchUser();
  }, []);

  const handleForgotPassword = async () => {
    try {
      await forgotPassword();
      toast.success("E-mail enviado com sucesso!");
    } catch (error) {
      const err = error as AxiosError;
      toast.error(err.message);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogTrigger asChild></DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="text-center">Configurações</DialogTitle>
        </DialogHeader>

        <Tabs defaultValue="account" aria-describedby="dialog-description">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="account">Conta</TabsTrigger>
            <TabsTrigger value="general">Geral</TabsTrigger>
          </TabsList>

          <TabsContent value="account">
            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">Nome</h2>
              <p>
                {user?.firstName} {user?.lastName}
              </p>
            </div>

            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">E-mail</h2>
              <p>{user?.email}</p>
            </div>

            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">Redefinir senha</h2>
              <Button onClick={handleForgotPassword} variant="outline">
                Redefinir
              </Button>
            </div>
          </TabsContent>

          <TabsContent value="general">
            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">Tema</h2>
              <ThemeToggle />
            </div>
          </TabsContent>
        </Tabs>
      </DialogContent>
    </Dialog>
  );
}
