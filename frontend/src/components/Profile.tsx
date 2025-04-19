import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useAuth } from "@/providers/AuthContext";
import { logout } from "@/services/authService";
import { AxiosError } from "axios";
import { LogOut, Settings, User2Icon } from "lucide-react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { SettingsDialog } from "./SettingsDialog";
import { Loader2 } from "lucide-react";

export function Profile() {
  const navigate = useNavigate();
  const { user, isLoading } = useAuth();
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);

  const handleSignOut = async () => {
    try {
      await logout();
      navigate("/signin");
    } catch (error) {
      const err = error as AxiosError;
      toast.error(err.message);
    }
  };

  const handleSettingsOpen = (e: Event) => {
    e.preventDefault();
    setIsSettingsOpen(true);
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="outline">
          <User2Icon className="h-4 w-4" />
          Perfil
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-56">
        <DropdownMenuLabel>
          {isLoading ? (
            <Loader2 className="h-4 w-4 animate-spin" />
          ) : (
            <>
              {user?.firstName} {user?.lastName}
            </>
          )}
        </DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuGroup>
          <DropdownMenuItem onSelect={handleSettingsOpen}>
            <div className="flex items-center justify-between w-full">
              Configurações
              <DropdownMenuShortcut>
                <Settings className="h-4 w-4" />
              </DropdownMenuShortcut>
            </div>
          </DropdownMenuItem>
          <DropdownMenuItem onClick={handleSignOut}>
            Sair
            <DropdownMenuShortcut>
              <LogOut className="h-4 w-4" />
            </DropdownMenuShortcut>
          </DropdownMenuItem>
        </DropdownMenuGroup>
      </DropdownMenuContent>
      <SettingsDialog open={isSettingsOpen} onOpenChange={setIsSettingsOpen} />
    </DropdownMenu>
  );
}
